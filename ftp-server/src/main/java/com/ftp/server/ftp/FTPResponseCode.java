package com.ftp.server.ftp;

public enum FTPResponseCode {
    WELCOME("220 Welcome to FTP server"),
    LOGIN_SUCCESSFUL("230 Login successful."),
    SPECIFY_PASSWORD("331 Please specify the password."),
    LOGIN_WITH_USER_PASS("530 Please login with USER and PASS."),
    CURRENT_DIRECTORY("257 \"%s\" is the current directory."),
    SWITCHING_TO_BINARY("200 Switching to Binary mode."),
    ENTERING_PASSIVE_MODE("227 Entering Passive Mode (%s)");

    private final String message;

    FTPResponseCode(String message) {
        this.message = message;
    }

    public String getMessage(Object... params) {
        return String.format(message, params);
    }

    @Override
    public String toString() {
        return message;
    }
}