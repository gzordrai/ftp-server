package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

import java.io.File;

/**
 * This class implements the RNFR (Rename From) command.
 * It specifies the old pathname of the file which is to be renamed.
 */
public class RnfrCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    /**
     * Constructs a new RnfrCommand.
     *
     * @param client the FTP client
     * @param args the command arguments
     */
    public RnfrCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    /**
     * Executes the RNFR command.
     * Specifies the old pathname of the file which is to be renamed.
     */
    @Override
    public void execute() {
        if (args.length > 1) {
            String path = args[1];
            File file = new File(path);

            if (file.exists() && file.isFile()) {
                this.client.setRenameFromFilePath(path);
                this.client.sendResponse(FTPResponseCode.FILE_EXISTS_READY_FOR_DESTINATION.toString());
            } else
                this.client.sendResponse(FTPResponseCode.FILE_NOT_FOUND.toString());
        } else
            this.client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
    }
}