package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

/**
 * This class implements the QUIT command.
 * It sends a goodbye message to the client and closes the connection.
 */
public class QuitCommand implements Command {
    private final FTPClient client;

    /**
     * Constructs a new QuitCommand.
     *
     * @param client the FTP client
     */
    public QuitCommand(FTPClient client) {
        this.client = client;
    }

    /**
     * Executes the QUIT command.
     * Sends a goodbye message to the client and closes the connection.
     */
    @Override
    public void execute() {
        this.client.sendResponse(FTPResponseCode.GOODBYE.toString());
        this.client.close();
    }
}