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

            if (this.client.getVolume().changeDirectory(newPath))
                this.client.sendResponse(FTPResponseCode.CURRENT_DIRECTORY.getMessage(newPath));
            else
                this.client.sendResponse(FTPResponseCode.FAILED_TO_CHANGE_DIRECTORY.toString());
        } else
            this.client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
    }
}