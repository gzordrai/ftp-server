package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

/**
 * This class implements the DELE (Delete) command.
 * It deletes a file on the server.
 */
public class DeleCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    /**
     * Constructs a new DeleCommand.
     *
     * @param client the FTP client
     * @param args the command arguments
     */
    public DeleCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    /**
     * Executes the DELE command.
     * Deletes a file on the server.
     */
    @Override
    public void execute() {
        if (args.length > 1) {
            String filePath = args[1];

            if (this.client.getVolume().deleteFile(filePath))
                client.sendResponse(FTPResponseCode.FILE_DELETED_SUCCESSFULLY.toString());
            else
                client.sendResponse(FTPResponseCode.FAILED_TO_DELETE_FILE.toString());
        } else
            client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
    }
}