package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

/**
 * This class implements the SYST command.
 * It sends the system type and a list of all available commands to the client.
 */
public class SystCommand implements Command {
    private final FTPClient client;

    /**
     * Constructs a new SystCommand.
     *
     * @param client the FTP client
     */
    public SystCommand(FTPClient client) {
        this.client = client;
    }

    /**
     * Executes the SYST command.
     * Sends the system type.
     */
    @Override
    public void execute() {
        String systemType = System.getProperty("os.name");

        this.client.sendResponse(FTPResponseCode.SYSTEM_TYPE.getMessage(systemType));
    }
}