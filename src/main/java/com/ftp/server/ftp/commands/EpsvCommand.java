package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;
import com.ftp.server.ftp.FTPStream;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EpsvCommand implements Command {
    private final FTPClient client;
    private final String[] args;

    public EpsvCommand(FTPClient client, String[] args) {
        this.client = client;
        this.args = args;
    }

    @Override
    public void execute() {
        try {
            if (client.getDataServerSocket() != null && !client.getDataServerSocket().isClosed())
                client.getDataServerSocket().close();

            client.setDataServerSocket(new ServerSocket(0)); // Bind to any available port

            int port = client.getDataServerSocket().getLocalPort();

            client.getCommandStream().write(FTPResponseCode.ENTERING_EXTENDED_PASSIVE_MODE.getMessage(port));

            Socket dataSocket = client.getDataServerSocket().accept();

            client.setDataStream(new FTPStream(dataSocket));
            client.getDataConnectionLatch().countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}