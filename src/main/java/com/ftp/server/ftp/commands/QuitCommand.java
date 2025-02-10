package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

public class QuitCommand implements Command {
    private final FTPClient client;

    public QuitCommand(FTPClient client) {
        this.client = client;
    }

    @Override
    public void execute() {
        this.client.sendResponse(FTPResponseCode.GOODBYE.toString());
        this.client.close();
    }
}