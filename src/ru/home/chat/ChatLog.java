package ru.home.chat;

import java.io.FileOutputStream;
import java.io.IOException;

public class ChatLog {
    private FileOutputStream outputStream;

    public ChatLog(String filePath) throws IOException {
        outputStream = new FileOutputStream(filePath, true);
    }

    public void addLogMessage(String message) throws IOException {
        if (outputStream == null) {
            return;
        }
        outputStream.write(message.getBytes());
    }

    public void closeLog() throws IOException {
        if (outputStream == null) {
            return;
        }
        outputStream.close();
    }


}
