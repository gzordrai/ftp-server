package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

/**
 * This class implements the MKD (Make Directory) command.
 * It creates a new directory on the server.
 */
public class MkdCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    /**
     * Constructs a new MkdCommand.
     *
     * @param client the FTP client
     * @param args the command arguments
     */
    public MkdCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    /**
     * Executes the MKD command.
     * Creates a new directory on the server.
     */
    @Override
    public void execute() {
        if (args.length > 1) {
            String dirPath = args[1];

            if (this.client.getVolume().createDirectory(dirPath))
                client.sendResponse(FTPResponseCode.DIRECTORY_CREATED.toString());
            else
                client.sendResponse(FTPResponseCode.FAILED_TO_CREATE_DIRECTORY.toString());

        } else
            client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
    }
}