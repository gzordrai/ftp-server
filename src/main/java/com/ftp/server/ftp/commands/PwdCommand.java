package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

public class PwdCommand implements Command {
    private final FTPClient client;

    public PwdCommand(FTPClient client) {
        this.client = client;
    }

    @Override
    public void execute() {
        // To change
        String currentDirectory = "/";
        client.sendResponse(FTPResponseCode.CURRENT_DIRECTORY.getMessage(currentDirectory));
    }
}