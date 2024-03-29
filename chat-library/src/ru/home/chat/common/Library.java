package ru.home.chat.common;

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
    public static final String CHANGE_LOGIN_REQUEST = "/change_login_request";
    public static final String CHANGE_LOGIN_SUCCESS = "/change_login_success";
    public static final String CHANGE_LOGIN_ERROR = "/change_login_error";
    public static final String MSG_FORMAT_ERROR = "/msg_format_error";
    // если мы вдруг не поняли, что за сообщение и не смогли разобрать
    public static final String TYPE_BROADCAST = "/bcast";
    // то есть сообщение, которое будет посылаться всем
    public static final String TYPE_BCAST_CLIENT = "/client_msg";
    public static final String USER_LIST = "/user_list";

    public static String getTypeBcastClient(String msg) {
        return TYPE_BCAST_CLIENT + DELIMITER + msg;
    }

    public static String getUserList(String users) {
        return USER_LIST + DELIMITER + users;
    }


    public static String getAuthRequest(String login, String password) {
        return AUTH_REQUEST + DELIMITER + login + DELIMITER + password;
    }

    public static String getAuthAccept(String nickname) {
        return AUTH_ACCEPT + DELIMITER + nickname;
    }

    public static String getAuthDenied() {
        return AUTH_DENIED;
    }

    public static String getChangeLoginRequest(String login, String newLogin) {
        return CHANGE_LOGIN_REQUEST + DELIMITER + login + DELIMITER + newLogin;
    }

    public static String getChangeLoginSuccess(String newLogin) {
        return CHANGE_LOGIN_SUCCESS + DELIMITER + newLogin;
    }

    public static String getChangeLoginError(String newLogin) {
        return CHANGE_LOGIN_ERROR + DELIMITER + newLogin;
    }

    public static String getMsgFormatError(String message) {
        return MSG_FORMAT_ERROR + DELIMITER + message;
    }

    public static String getTypeBroadcast(String src, String message) {
        return TYPE_BROADCAST + DELIMITER + System.currentTimeMillis() +
                DELIMITER + src + DELIMITER + message;
    }

}
