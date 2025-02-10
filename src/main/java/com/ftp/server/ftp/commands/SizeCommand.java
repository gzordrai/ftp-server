package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

/**
 * This class implements the SIZE (Size) command.
 * It returns the size of a file on the server.
 */
public class SizeCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    /**
     * Constructs a new SizeCommand.
     *
     * @param client the FTP client
     * @param args the command arguments
     */
    public SizeCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    /**
     * Executes the SIZE command.
     * Returns the size of a file on the server.
     */
    @Override
    public void execute() {
        if (args.length > 1) {
            String filePath = args[1];
            long size = this.client.getVolume().getFileSize(filePath);
            
            if (size != -1)
                client.sendResponse(FTPResponseCode.FILE_SIZE.getMessage(size));
            else
                client.sendResponse(FTPResponseCode.FILE_NOT_FOUND.getMessage());
        } else {
            client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
        }
    }
}