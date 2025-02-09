package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;
import com.ftp.server.ftp.FTPStream;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PasvCommand implements Command {
    private final FTPClient client;

    public PasvCommand(FTPClient client) {
        this.client = client;
    }

    @Override
    public void execute() {
        try {
            if (client.getDataServerSocket() != null && !client.getDataServerSocket().isClosed())
                client.getDataServerSocket().close();

            client.setDataServerSocket(new ServerSocket(0)); // Bind to any available port

            int port = client.getDataServerSocket().getLocalPort();
            String hostAddress = client.getCommandStream().getSocket().getLocalAddress().getHostAddress().replace('.',
                    ',');

            client.getCommandStream()
                    .write(FTPResponseCode.ENTERING_PASSIVE_MODE.getMessage(hostAddress, port / 256, port % 256));

            Socket dataSocket = client.getDataServerSocket().accept();

            client.setDataStream(new FTPStream(dataSocket));
            client.getDataConnectionLatch().countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}