package ru.home.chat.server.core;

import org.apache.logging.log4j.Level;

public interface ChatServerListener {
    void onChatServerMessage(Level level, String msg);
}
