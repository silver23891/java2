package ru.home.chat.server.gui.fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.home.chat.server.core.ChatServer;
import ru.home.chat.server.core.ChatServerListener;

public class Controller implements ChatServerListener {

    @FXML
    public TextArea taLog;

    private ChatServer server = new ChatServer(this);
    private Logger logger = LogManager.getLogger(Controller.class);

    public void start(ActionEvent actionEvent) {
        server.start(8189);
    }

    public void stop(ActionEvent actionEvent) {
        server.stop();
    }

    public void drop(ActionEvent actionEvent) {
        server.dropAllClients();
    }

    @Override
    public void onChatServerMessage(Level level, String msg) {
        taLog.appendText(msg + "\n");
        logger.log(level, msg);
    }
}
