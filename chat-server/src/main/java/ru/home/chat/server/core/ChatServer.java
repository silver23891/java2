package ru.home.chat.server.core;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.home.chat.common.Library;
import ru.home.network.ServerSocketThread;
import ru.home.network.ServerSocketThreadListener;
import ru.home.network.SocketThread;
import ru.home.network.SocketThreadListener;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {
    ExecutorService server = Executors.newSingleThreadExecutor();
    Future<?> serverStatus;
    ChatServerListener listener;
    Vector<SocketThread> clients = new Vector<>();
    final long UNAUTHORIZED_TIMEOUT = 120000;
    Logger logger = LogManager.getLogger(ChatServer.class);

    public ChatServer(ChatServerListener listener) {
        this.listener = listener;
    }

    public void start(int port) {
        if (serverStatus != null && !serverStatus.isDone()) {
            putLog(Level.INFO, "Already running");
        } else {
            serverStatus = server.submit(new ServerSocketThread(this, "Server", port, 2000));
        }
    }

    public void stop() {
        if (serverStatus == null || serverStatus.isDone()) {
            putLog(Level.INFO, "Nothing to stop");
        } else {
            //Евгений, не могу понять почему не останавливается поток. В дебагере interrupted устанавливается в true
            //Подскажите, пожалуйста
            server.shutdownNow();
        }
    }

    private void putLog(Level level, String msg) {
        listener.onChatServerMessage(level, msg);
    }

    /**
     * Server Socket Thread Listener methods
     * */

    @Override
    public void onServerStarted(ServerSocketThread thread) {
        putLog(Level.INFO, "Server thread started");
        SqlClient.connect();
    }

    @Override
    public void onServerCreated(ServerSocketThread thread, ServerSocket server) {
        putLog(Level.INFO, "Server socket started");
    }

    @Override
    public void onServerTimeout(ServerSocketThread thread, ServerSocket server) {
        dropUnauthorizedClients();
    }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, ServerSocket server, Socket socket) {
        putLog(Level.INFO, "Client connected");
        String name = "SocketThread " + socket.getInetAddress() + ":" + socket.getPort();
        new ClientThread(this, name, socket);
    }

    @Override
    public void onServerException(ServerSocketThread thread, Throwable throwable) {
        putLog(Level.ERROR, throwable.getCause().getMessage());
    }

    @Override
    public void onServerStop(ServerSocketThread thread) {
        putLog(Level.INFO, "Server thread stopped");
        dropAllClients();
        SqlClient.disconnect();
    }

    /**
     * Socket Thread Listener methods
     * */

    @Override
    public synchronized void onSocketStart(SocketThread thread, Socket socket) {
        putLog(Level.INFO, "Socket started");
    }

    @Override
    public synchronized void onSocketStop(SocketThread thread) {
        ClientThread client = (ClientThread) thread;
        clients.remove(thread);
        if (client.isAuthorized() && !client.isReconnecting()) {
            sendToAllAuthorizedClients(Library.getTypeBroadcast("Server",
                    client.getNickname() + " disconnected"));
        }
        sendToAllAuthorizedClients(Library.getUserList(getUsers()));
    }

    @Override
    public synchronized void onSocketReady(SocketThread thread, Socket socket) {
        putLog(Level.INFO, "Socket ready");
        clients.add(thread);
        ((ClientThread) thread).setSocketReadyTime();
    }

    @Override
    public synchronized void onReceiveString(SocketThread thread, Socket socket, String msg) {
        ClientThread client = (ClientThread) thread;
        if (client.isAuthorized()) {
            handleAuthMessage(client, msg);
        } else
            handleNonAuthMessage(client, msg);
    }

    @Override
    public synchronized void onSocketException(SocketThread thread, Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        putLog(Level.ERROR, stringWriter.toString());
    }

    void handleAuthMessage(ClientThread client, String msg) {
        putLog(Level.INFO, "Receive message from " + client.getNickname() + ": " + msg);
        String[] arr = msg.split(Library.DELIMITER);
        String msgType = arr[0];
        switch (msgType) {
            case Library.TYPE_BCAST_CLIENT:
                sendToAllAuthorizedClients(Library.getTypeBroadcast(
                        client.getNickname(), arr[1]));
                break;
            case Library.CHANGE_LOGIN_REQUEST:
                if (changeLogin(arr[1], arr[2])) {
                    client.sendMessage(Library.getChangeLoginSuccess(arr[2]));
                } else {
                    client.sendMessage(Library.getChangeLoginError(arr[2]));
                }
                break;
            default:
                client.sendMessage(Library.getMsgFormatError(msg));
        }

    }

    void handleNonAuthMessage(ClientThread client, String msg) {
        putLog(Level.INFO, "Receive unauthorized message: " + msg);
        String[] arr = msg.split(Library.DELIMITER);
        if (arr.length != 3 || !arr[0].equals(Library.AUTH_REQUEST)) {
            client.msgFormatError(msg);
            return;
        }
        String login = arr[1];
        String password = arr[2];
        String nickname = SqlClient.getNickname(login, password);
        if (nickname == null) {
            putLog(Level.WARN, "Invalid login attempt: " + login);
            client.authFail();
            return;
        } else {
            ClientThread oldClient = findClientByNickname(nickname);
            client.authAccept(nickname);
            if (oldClient == null) {
                sendToAllAuthorizedClients(Library.getTypeBroadcast("Server", nickname + " connected"));
            } else {
                oldClient.reconnect();
                clients.remove(oldClient);
            }
        }
        sendToAllAuthorizedClients(Library.getUserList(getUsers()));
    }

    private void sendToAllAuthorizedClients(String msg) {
        for (int i = 0; i < clients.size(); i++) {
            ClientThread client = (ClientThread) clients.get(i);
            if (!client.isAuthorized()) continue;
            client.sendMessage(msg);
        }
    }

    public void dropAllClients() {
        for (int i = 0; i < clients.size(); i++) {
            clients.get(i).close();
        }
    }


    private synchronized String getUsers() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < clients.size(); i++) {
            ClientThread client = (ClientThread) clients.get(i);
            if (!client.isAuthorized()) continue;
            sb.append(client.getNickname()).append(Library.DELIMITER);
        }
        return sb.toString();
    }


    private synchronized ClientThread findClientByNickname(String nickname) {
        for (int i = 0; i < clients.size(); i++) {
            ClientThread client = (ClientThread) clients.get(i);
            if (!client.isAuthorized()) continue;
            if (client.getNickname().equals(nickname))
                return client;
        }
        return null;
    }

    private void dropUnauthorizedClients() {
        for (int i = 0; i < clients.size(); i++) {
            ClientThread client = (ClientThread) clients.get(i);
            if (client.isAuthorized() ||
                    (System.currentTimeMillis() - client.getSocketReadyTime()) < UNAUTHORIZED_TIMEOUT) {
                continue;
            }
            client.close();
        }
    }

    private boolean changeLogin(String login, String newLogin) {
        return SqlClient.updateNickname(login, newLogin);
    }
}
