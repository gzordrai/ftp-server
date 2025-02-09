package com.ftp.server.ftp;

public enum FTPResponseCode {
    WELCOME("220 Welcome to FTP server"),
    LOGIN_SUCCESSFUL("230 Login successful."),
    SPECIFY_PASSWORD("331 Please specify the password."),
    LOGIN_WITH_USER_PASS("530 Please login with USER and PASS."),
    CURRENT_DIRECTORY("257 \"%s\" is the current directory."),
    SWITCHING_TO_BINARY("200 Switching to Binary mode."),
    ENTERING_PASSIVE_MODE("227 Entering Passive Mode (%s)"),
    TRANSFER_START("150 Here comes the directory listing."),
    DIRECTORY_SENDED("226 Directory send OK"),
    SYNTAX_ERROR("501 Syntax error in parameters or arguments."),
    ENTERING_EXTENDED_PASSIVE_MODE("229 Entering Extended Passive Mode (|||%d|)"),;

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