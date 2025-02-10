package com.ftp.server.ftp.commands;

import java.util.StringJoiner;

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
            StringJoiner joiner = new StringJoiner(" ");

            for (int i = 1; i < args.length; i++)
                joiner.add(args[i]);

            String newPath = joiner.toString();

            if (client.getVolume().changeDirectory(newPath))
                client.sendResponse(FTPResponseCode.CURRENT_DIRECTORY.getMessage(newPath));
            else
                client.sendResponse("550 Failed to change directory.");
        } else
            client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
    }
}