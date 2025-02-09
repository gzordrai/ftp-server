package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

public class AuthCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    public AuthCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    @Override
    public void execute() {
        if (args.length > 1 && "SSL".equalsIgnoreCase(args[1]))
            client.sendResponse(FTPResponseCode.LOGIN_WITH_USER_PASS.toString());// client.sendResponse("234 AUTH SSL successful");
        else
            client.sendResponse(FTPResponseCode.LOGIN_WITH_USER_PASS.toString());
    }
}