package com.ftp.server.ftp;

public enum FTPResponseCode {
    /* Success Responses */
    WELCOME("220 Welcome to FTP server"),
    LOGIN_SUCCESSFUL("230 Login successful."),
    SPECIFY_PASSWORD("331 Please specify the password."),
    SYSTEM_TYPE("215 %s"),
    FEATURES_SUPPORTED("211-Features:\n%s\n211 End"),
    CURRENT_DIRECTORY("257 \"%s\" is the current directory."),
    SWITCHING_TO_ASCII("200 Switching to Ascii mode."),
    SWITCHING_TO_BINARY("200 Switching to Binary mode."),
    ENTERING_PASSIVE_MODE("227 Entering Passive Mode (%s)"),
    TRANSFER_START("150 Here comes the directory listing."),
    DIRECTORY_SENDED("226 Directory send OK"),
    ENTERING_EXTENDED_PASSIVE_MODE("229 Entering Extended Passive Mode (|||%d|)"),
    GOODBYE("221 Goodbye."),
    FILE_EXISTS_READY_FOR_DESTINATION("350 File exists, ready for destination name."),
    FILE_RENAMED_SUCCESSFULLY("250 File renamed successfully."),
    FILE_DELETED_SUCCESSFULLY("250 File deleted successfully."),
    DIRECTORY_CREATED("257 Directory created."),
    FILE_SIZE("213 %d"),

    /* Error Responses */
    SYNTAX_ERROR("501 Syntax error in parameters or arguments."),
    LOGIN_WITH_USER_PASS("530 Please login with USER and PASS."),
    NOT_LOGGED_IN("530 Not logged in."),
    FILE_NOT_FOUND("550 File not found."),
    FAILED_TO_CHANGE_DIRECTORY("550 Failed to change directory."),
    FAILED_TO_RENAME_FILE("550 Failed to rename file."),
    BAD_SEQUENCE_OF_COMMANDS("503 Bad sequence of commands."),
    FAILED_TO_DELETE_FILE("550 Failed to delete file."),
    FAILED_TO_CREATE_DIRECTORY("550 Failed to create directory."),;

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