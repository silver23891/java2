package ru.home.chat.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Library {
    /*
    * /auth_request±login±password
    * /auth_accept±nickname
    * /auth_denied
    * /broadcast±msg
    *
    * /msg_format_error±msg
    * */

    public static final String DELIMITER = "±";
    public static final String AUTH_REQUEST = "/auth_request";
    public static final String AUTH_ACCEPT = "/auth_accept";
    public static final String AUTH_DENIED = "/auth_denied";
    public static final String MSG_FORMAT_ERROR = "/msg_format_error";
    // если мы вдруг не поняли, что за сообщение и не смогли разобрать
    public static final String TYPE_BROADCAST = "/bcast";
    // то есть сообщение, которое будет посылаться всем

    public static String getAuthRequest(String login, String password) {
        return AUTH_REQUEST + DELIMITER + login + DELIMITER + password;
    }

    public static String getAuthAccept(String nickname) {
        return AUTH_ACCEPT + DELIMITER + nickname;
    }

    public static String getAuthDenied() {
        return AUTH_DENIED;
    }

    public static String getMsgFormatError(String message) {
        return MSG_FORMAT_ERROR + DELIMITER + message;
    }

    public static String getTypeBroadcast(String src, String message) {
        return TYPE_BROADCAST + DELIMITER + System.currentTimeMillis() +
                DELIMITER + src + DELIMITER + message;
    }

    public static HashMap<String, String> getMessageParams(String message) {
        HashMap<String, String> hashMap = new HashMap<>();
        String[] messageParts = message.split(DELIMITER);
        if (messageParts[0].equals(AUTH_REQUEST) && messageParts.length == 3) {
            hashMap.put("type", AUTH_REQUEST);
            hashMap.put("login", messageParts[1]);
            hashMap.put("password", messageParts[2]);
        } else if (messageParts[0].equals(AUTH_ACCEPT) && messageParts.length == 2) {
            hashMap.put("type", AUTH_ACCEPT);
            hashMap.put("login", messageParts[1]);
            hashMap.put("clientMessage", "Успешный вход под пользователем " + messageParts[1]);
        } else if (messageParts[0].equals(AUTH_DENIED)) {
            hashMap.put("type", AUTH_DENIED);
            hashMap.put("clientMessage", "Неверное имя пользователя или пароль");
        } else if (messageParts[0].equals(MSG_FORMAT_ERROR) && messageParts.length == 2) {
            hashMap.put("type", MSG_FORMAT_ERROR);
            hashMap.put("clientMessage", "Ошибка в формате сообщения: " + messageParts[1]);
        } else if (messageParts[0].equals(TYPE_BROADCAST) && messageParts.length == 4) {
            hashMap.put("type", TYPE_BROADCAST);
            Date date = new Date(Long.parseLong(messageParts[1]));
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            hashMap.put(
                    "clientMessage",
                    String.format("%s Сообщение от %s:\n%s", sdf.format(date), messageParts[2], messageParts[3])
            );
        } else {
            hashMap.put("type", "unknown");
            hashMap.put("clientMessage", message);
        }
        return hashMap;
    }

}
