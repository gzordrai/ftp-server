package com.ftp.server.ftp.commands;

import com.ftp.server.ftp.FTPClient;
import com.ftp.server.ftp.FTPResponseCode;
import com.ftp.server.ftp.FTPStream;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class implements the PASV (Passive Mode) command.
 * It sets the server to passive mode and waits for a data connection from the client.
 */
public class PasvCommand implements Command {
    private final FTPClient client;

    /**
     * Constructs a new PasvCommand.
     *
     * @param client the FTP client
     */
    public PasvCommand(FTPClient client) {
        this.client = client;
    }

    /**
     * Executes the PASV command.
     * Sets the server to passive mode and waits for a data connection from the client.
     */
    @Override
    public void execute() {
        try {
            if (this.client.getDataServerSocket() != null && !this.client.getDataServerSocket().isClosed())
                this.client.getDataServerSocket().close();

            this.client.setDataServerSocket(new ServerSocket(0)); // Bind to any available port

            int port = this.client.getDataServerSocket().getLocalPort();
            String hostAddress = this.client.getCommandStream().getSocket().getLocalAddress().getHostAddress().replace('.', ',');

            this.client.getCommandStream()
                    .write(FTPResponseCode.ENTERING_PASSIVE_MODE.getMessage(hostAddress, port / 256, port % 256));

            Socket dataSocket = this.client.getDataServerSocket().accept();

            this.client.setDataStream(new FTPStream(dataSocket));
            this.client.getDataConnectionLatch().countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}