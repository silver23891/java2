package ru.home.chat.server.core;

import ru.home.chat.common.Library;
import ru.home.network.SocketThread;
import ru.home.network.SocketThreadListener;

import java.net.Socket;

public class ClientThread extends SocketThread {

    private String nickname;
    private boolean isAuthorized;
    private boolean isReconnecting;
    private long socketReadyTime;

    public ClientThread(SocketThreadListener listener, String name, Socket socket) {
        super(listener, name, socket);
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public boolean isReconnecting() {
        return isReconnecting;
    }

    void reconnect() {
        isReconnecting = true;
        close();
    }

    public void authAccept(String nickname) {
        isAuthorized = true;
        this.nickname = nickname;
        sendMessage(Library.getAuthAccept(nickname));
    }

    public void authFail() {
        sendMessage(Library.getAuthDenied());
        close();
    }

    public void msgFormatError(String msg) {
        sendMessage(Library.getMsgFormatError(msg));
        close();
    }

    public long getSocketReadyTime() {
        return socketReadyTime;
    }

    public void setSocketReadyTime() {
        this.socketReadyTime = System.currentTimeMillis();
    }

}
