package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

/**
 * This class implements the RNTO (Rename To) command.
 * It specifies the new pathname for the file which is to be renamed.
 */
public class RntoCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    /**
     * Constructs a new RntoCommand.
     *
     * @param client the FTP client
     * @param args the command arguments
     */
    public RntoCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    /**
     * Executes the RNTO command.
     * Specifies the new pathname for the file which is to be renamed.
     */
    @Override
    public void execute() {
        if (args.length > 1) {
            String newPath = args[1];
            String oldPath = this.client.getRenameFromFilePath();

            if (oldPath != null) {
                boolean success = this.client.getVolume().renameFile(oldPath, newPath);

                if (success)
                    this.client.sendResponse(FTPResponseCode.FILE_RENAMED_SUCCESSFULLY.toString());
                else
                    this.client.sendResponse(FTPResponseCode.FAILED_TO_RENAME_FILE.toString());
            } else
                this.client.sendResponse(FTPResponseCode.BAD_SEQUENCE_OF_COMMANDS.toString());
        } else
            this.client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
    }
}