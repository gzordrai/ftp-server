package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;

public class SystCommand implements Command {
    private final FTPClient client;

    public SystCommand(FTPClient client) {
        this.client = client;
    }

    @Override
    public void execute() {
        client.sendResponse("215 UNIX Type: L8");
    }
}