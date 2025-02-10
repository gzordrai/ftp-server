package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

/**
 * This class implements the USER command.
 * It handles the username input from the client.
 */
public class UserCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    /**
     * Constructs a new UserCommand.
     *
     * @param client the FTP client
     * @param args the command arguments
     */
    public UserCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    /**
     * Executes the USER command.
     * Handles the username input from the client.
     */
    @Override
    public void execute() {
        if (args.length > 1) {
            String username = args[1];

            this.client.sendResponse(FTPResponseCode.SPECIFY_PASSWORD.toString());
        } else
            this.client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
    }
}