package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

/**
 * This class implements the CDUP (Change to Parent Directory) command.
 * It changes the current directory of the FTP client to the parent directory.
 */
public class CdupCommand implements Command {
    private final FTPClient client;

    /**
     * Constructs a new CdupCommand.
     *
     * @param client the FTP client
     */
    public CdupCommand(FTPClient client) {
        this.client = client;
    }

    /**
     * Executes the CDUP command.
     * Changes the current directory of the FTP client to the parent directory.
     */
    @Override
    public void execute() {
        this.client.getVolume().changeToParentDirectory();
        this.client.sendResponse(FTPResponseCode.CURRENT_DIRECTORY
                .getMessage(this.client.getVolume().getCurrentDirectory().getAbsolutePath()));
    }
}