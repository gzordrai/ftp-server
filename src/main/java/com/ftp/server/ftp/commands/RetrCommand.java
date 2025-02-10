package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This class implements the RETR (Retrieve) command.
 * It handles the downloading of a file from the server to the client.
 */
public class RetrCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    /**
     * Constructs a new RetrCommand.
     *
     * @param client the FTP client
     * @param args the command arguments
     */
    public RetrCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    /**
     * Executes the RETR command.
     * Handles the downloading of a file from the server to the client.
     */
    @Override
    public void execute() {
        if (args.length > 1) {
            String filePath = args[1];
            File file = new File(filePath);

            if (file.exists() && file.isFile()) {
                try (FileInputStream fis = new FileInputStream(file);
                    OutputStream os = this.client.getDataStream().getOutputStream()) {

                    this.client.sendResponse(FTPResponseCode.OPENING_DATA_CONNECTION.toString());

                    byte[] buffer = new byte[8192]; // 8 KB buffer size
                    int bytesRead;

                    while ((bytesRead = fis.read(buffer)) != -1)
                        os.write(buffer, 0, bytesRead);

                    this.client.getDataStream().close();
                    this.client.setDataStream(null);

                    this.client.sendResponse(FTPResponseCode.TRANSFER_COMPLETE.toString());
                } catch (IOException e) {
                    this.client.sendResponse(FTPResponseCode.REQUESTED_ACTION_ABORTED.toString());
                }
            } else
                this.client.sendResponse(FTPResponseCode.FILE_NOT_FOUND.toString());
        } else
            this.client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
    }
}