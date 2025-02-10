package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

/**
 * This class implements the PASS command.
 * It handles the password input from the client.
 */
public class PassCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    /**
     * Constructs a new PassCommand.
     *
     * @param client the FTP client
     * @param args the command arguments
     */
    public PassCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    /**
     * Executes the PASS command.
     * Handles the password input from the client.
     */
    @Override
    public void execute() {
        if (args.length > 1) {
            String password = args[1];

            this.client.sendResponse(FTPResponseCode.LOGIN_SUCCESSFUL.toString());
        } else
            this.client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
    }
}