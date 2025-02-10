package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

/**
 * This class implements the AUTH command.
 * It handles authentication mechanisms such as SSL.
 */
public class AuthCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    /**
     * Constructs a new AuthCommand.
     *
     * @param client the FTP client
     * @param args the command arguments
     */
    public AuthCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    /**
     * Executes the AUTH command.
     * Handles authentication mechanisms such as SSL.
     */
    @Override
    public void execute() {
        if (args.length > 1 && "SSL".equalsIgnoreCase(args[1]))
            this.client.sendResponse(FTPResponseCode.LOGIN_WITH_USER_PASS.toString()); // this.client.sendResponse("234 AUTH SSL successful");
        else
            this.client.sendResponse(FTPResponseCode.LOGIN_WITH_USER_PASS.toString());
    }
}