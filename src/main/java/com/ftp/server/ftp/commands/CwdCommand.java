package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

public class CwdCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    public CwdCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    @Override
    public void execute() {
        if (args.length > 1) {
            String newPath = args[1];

            if (client.getVolume().changeDirectory(newPath))
                client.sendResponse(String.format(FTPResponseCode.CURRENT_DIRECTORY.getMessage(), newPath));
            else
                client.sendResponse("550 Failed to change directory.");
        } else
            client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
    }
}