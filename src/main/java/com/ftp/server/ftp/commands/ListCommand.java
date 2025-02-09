package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;

import java.io.File;
import java.io.IOException;

public class ListCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    public ListCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    @Override
    public void execute() {
        try {
            client.sendResponse(FTPResponseCode.TRANSFER_START.toString());
            StringBuilder files = new StringBuilder();

            for (File f : client.getVolume().listFiles()) {
                if (f.isDirectory()) {
                    files.append("drwxr-xr-x 1 owner group ").append(f.length()).append(" Jan 1 00:00 ")
                            .append(f.getName()).append("\r\n");
                } else {
                    files.append("-rw-r--r-- 1 owner group ").append(f.length()).append(" Jan 1 00:00 ")
                            .append(f.getName()).append("\r\n");
                }
            }

            if (client.getDataConnectionLatch() != null)
                client.getDataConnectionLatch().await();

            if (client.getDataStream() != null) {
                client.getDataStream().write(files.toString());
                client.getDataStream().close();
                client.setDataStream(null);
            } else
                client.sendResponse("425 Can't open data connection.");

            client.sendResponse(FTPResponseCode.DIRECTORY_SENDED.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}