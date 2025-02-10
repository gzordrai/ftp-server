package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

/**
 * This class implements the MDTM (Modification Time) command.
 * It returns the last modification time of a file on the server.
 */
public class MdtmCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    /**
     * Constructs a new MdtmCommand.
     *
     * @param client the FTP client
     * @param args the command arguments
     */
    public MdtmCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    /**
     * Executes the MDTM command.
     * Returns the last modification time of a file on the server.
     */
    @Override
    public void execute() {
        if (args.length > 1) {
            String filePath = args[1];
            String date = this.client.getVolume().getLastTimeModified(filePath);

            if (date != null)
                client.sendResponse(FTPResponseCode.FILE_LAST_MODIFIED.getMessage(date));
            else
                client.sendResponse(FTPResponseCode.FILE_NOT_FOUND.getMessage());
        } else
            client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
    }
}