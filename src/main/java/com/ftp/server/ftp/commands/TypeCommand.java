package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

/**
 * This class implements the TYPE command.
 * It sets the transfer mode to either ASCII or binary.
 */
public class TypeCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    /**
     * Constructs a new TypeCommand.
     *
     * @param client the FTP client
     * @param args   the command arguments
     */
    public TypeCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    /**
     * Executes the TYPE command.
     * Sets the transfer mode to either ASCII or binary.
     */
    @Override
    public void execute() {
        if (args.length > 1) {
            String type = args[1].toUpperCase();

            switch (type) {
                case "A":
                    this.client.sendResponse(FTPResponseCode.SWITCHING_TO_ASCII.toString());
                    break;

                case "I":
                    this.client.sendResponse(FTPResponseCode.SWITCHING_TO_BINARY.toString());
                    break;

                default:
                    this.client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
                    break;
            }
        } else
            this.client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
    }
}