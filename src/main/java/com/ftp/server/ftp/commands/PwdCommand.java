package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

/**
 * This class implements the PWD (Print Working Directory) command.
 * It sends the current directory of the FTP client.
 */
public class PwdCommand implements Command {
    private final FTPClient client;

    /**
     * Constructs a new PwdCommand.
     *
     * @param client the FTP client
     */
    public PwdCommand(FTPClient client) {
        this.client = client;
    }

    /**
     * Executes the PWD command.
     * Sends the current directory of the FTP client.
     */
    @Override
    public void execute() {
        this.client.sendResponse(
                FTPResponseCode.CURRENT_DIRECTORY.getMessage(this.client.getVolume().getCurrentDirectory()));
    }
}