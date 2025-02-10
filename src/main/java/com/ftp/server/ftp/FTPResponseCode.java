package com.ftp.server.ftp;

/**
 * Enum representing FTP response codes and their messages.
 */
public enum FTPResponseCode {
    /* Success Responses */
    /**
     * 220 Welcome to FTP server.
     */
    WELCOME("220 Welcome to FTP server"),
    /**
     * 230 Login successful.
     */
    LOGIN_SUCCESSFUL("230 Login successful."),
    /**
     * 331 Please specify the password.
     */
    SPECIFY_PASSWORD("331 Please specify the password."),
    /**
     * 215 System type.
     */
    SYSTEM_TYPE("215 %s"),
    /**
     * 211 Features supported.
     */
    FEATURES_SUPPORTED("211-Features:\n%s\n211 End"),
    /**
     * 257 Current directory.
     */
    CURRENT_DIRECTORY("257 \"%s\" is the current directory."),
    /**
     * 200 Switching to ASCII mode.
     */
    SWITCHING_TO_ASCII("200 Switching to Ascii mode."),
    /**
     * 200 Switching to Binary mode.
     */
    SWITCHING_TO_BINARY("200 Switching to Binary mode."),
    /**
     * 227 Entering Passive Mode.
     */
    ENTERING_PASSIVE_MODE("227 Entering Passive Mode (%s)"),
    /**
     * 150 Opening data connection.
     */
    TRANSFER_START("150 Opening data connection."),
    /**
     * 226 Directory send OK.
     */
    DIRECTORY_SENDED("226 Directory send OK"),
    /**
     * 229 Entering Extended Passive Mode.
     */
    ENTERING_EXTENDED_PASSIVE_MODE("229 Entering Extended Passive Mode (|||%d|)"),
    /**
     * 221 Goodbye.
     */
    GOODBYE("221 Goodbye."),
    /**
     * 350 File exists, ready for destination name.
     */
    FILE_EXISTS_READY_FOR_DESTINATION("350 File exists, ready for destination name."),
    /**
     * 250 File renamed successfully.
     */
    FILE_RENAMED_SUCCESSFULLY("250 File renamed successfully."),
    /**
     * 250 File deleted successfully.
     */
    FILE_DELETED_SUCCESSFULLY("250 File deleted successfully."),
    /**
     * 257 Directory created.
     */
    DIRECTORY_CREATED("257 Directory created."),
    /**
     * 213 File size.
     */
    FILE_SIZE("213 %d"),
    /**
     * 213 File last modified.
     */
    FILE_LAST_MODIFIED("213 %s"),
    /**
     * 150 Opening data connection.
     */
    OPENING_DATA_CONNECTION("150 Opening data connection."),
    /**
     * 226 Transfer complete.
     */
    TRANSFER_COMPLETE("226 Transfer complete."),

    /* Error Responses */
    /**
     * 501 Syntax error in parameters or arguments.
     */
    SYNTAX_ERROR("501 Syntax error in parameters or arguments."),
    /**
     * 503 Bad sequence of commands.
     */
    BAD_SEQUENCE_OF_COMMANDS("503 Bad sequence of commands."),
    /**
     * 451 Requested action not taken.
     */
    REQUESTED_ACTION_ABORTED("451 Requested action not taken."),
    /**
     * 530 Please login with USER and PASS.
     */
    LOGIN_WITH_USER_PASS("530 Please login with USER and PASS."),
    /**
     * 530 Not logged in.
     */
    NOT_LOGGED_IN("530 Not logged in."),
    /**
     * 550 File not found.
     */
    FILE_NOT_FOUND("550 File not found."),
    /**
     * 550 Failed to change directory.
     */
    FAILED_TO_CHANGE_DIRECTORY("550 Failed to change directory."),
    /**
     * 550 Failed to rename file.
     */
    FAILED_TO_RENAME_FILE("550 Failed to rename file."),
    /**
     * 550 Failed to delete file.
     */
    FAILED_TO_DELETE_FILE("550 Failed to delete file."),
    /**
     * 550 Failed to create directory.
     */
    FAILED_TO_CREATE_DIRECTORY("550 Failed to create directory.");

    private final String message;

    FTPResponseCode(String message) {
        this.message = message;
    }

    /**
     * Returns the formatted message with the given parameters.
     *
     * @param params the parameters to format the message with
     * @return the formatted message
     */
    public String getMessage(Object... params) {
        return String.format(message, params);
    }

    @Override
    public String toString() {
        return message;
    }
}