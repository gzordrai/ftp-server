package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class implements the STOR (Store) command.
 * It handles the uploading of a file to the server.
 */
public class StorCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    /**
     * Constructs a new StorCommand.
     *
     * @param client the FTP client
     * @param args the command arguments
     */
    public StorCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    /**
     * Executes the STOR command.
     * Handles the uploading of a file to the server.
     */
    @Override
    public void execute() {
        if (args.length > 1) {
            String filePath = args[1];
            File file = new File(filePath);

            try (FileOutputStream fos = new FileOutputStream(file);
                InputStream is = this.client.getDataStream().getInputStream()) {

                this.client.sendResponse(FTPResponseCode.OPENING_DATA_CONNECTION.toString());

                byte[] buffer = new byte[8192]; // 8 KB buffer size
                int bytesRead;

                while ((bytesRead = is.read(buffer)) != -1)
                    fos.write(buffer, 0, bytesRead);

                this.client.getDataStream().close();
                this.client.setDataStream(null);

                this.client.sendResponse(FTPResponseCode.TRANSFER_COMPLETE.toString());
            } catch (IOException e) {
                this.client.sendResponse("451 Requested action aborted: local error in processing.");
            }
        } else
            this.client.sendResponse(FTPResponseCode.SYNTAX_ERROR.toString());
    }
}