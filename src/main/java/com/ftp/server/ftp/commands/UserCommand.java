package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

public class UserCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    public UserCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    @Override
    public void execute() {
        if (args.length > 1) {
            String username = args[1];

            client.sendResponse(FTPResponseCode.SPECIFY_PASSWORD.toString());
        } else {
            client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
        }
    }
}