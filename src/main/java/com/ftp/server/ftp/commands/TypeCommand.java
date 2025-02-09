package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

public class TypeCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    public TypeCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    @Override
    public void execute() {
        if (args.length > 1) {
            String type = args[1].toUpperCase();

            switch (type) {
                case "A":
                    client.sendResponse("200 Switching to ASCII mode.");
                    break;

                case "I":
                    client.sendResponse(FTPResponseCode.SWITCHING_TO_BINARY.toString());
                    break;

                default:
                    client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
                    break;
            }
        } else
            client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
    }
}