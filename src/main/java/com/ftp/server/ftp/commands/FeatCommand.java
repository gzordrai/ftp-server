package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPCommandFactory;
import com.ftp.server.ftp.FTPResponseCode;

import java.util.Set;

/**
 * This class implements the FEAT command.
 * It sends a list of all available features (commands) to the client.
 */
public class FeatCommand implements Command {
    private final FTPClient client;

    /**
     * Constructs a new FeatCommand.
     *
     * @param client the FTP client
     */
    public FeatCommand(FTPClient client) {
        this.client = client;
    }

    /**
     * Executes the FEAT command.
     * Sends a list of all available features (commands) to the client.
     */
    @Override
    public void execute() {
        StringBuilder response = new StringBuilder();
        Set<String> commands = FTPCommandFactory.getCommandHandlers();

        for (String command : commands)
            response.append(" ").append(command).append("\n");

        this.client.sendResponse(FTPResponseCode.FEATURES_SUPPORTED.getMessage(response.toString()));
    }
}