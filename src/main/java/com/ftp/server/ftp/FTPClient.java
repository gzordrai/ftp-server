package com.ftp.server.ftp;

import com.ftp.server.fs.Volume;
import com.ftp.server.ftp.commands.Command;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CountDownLatch;

/**
 * This class represents an FTP client.
 * It handles FTP commands and data transfer.
 */
public class FTPClient implements Runnable {
    private FTPStream commandStream;
    private FTPStream dataStream;
    private ServerSocket dataServerSocket;
    private CountDownLatch dataConnectionLatch;
    private String fileToRenamePath;

    /**
     * Constructs a new FTPClient.
     *
     * @param socket the client socket
     * @throws IOException if an I/O error occurs
     */
    public FTPClient(Socket socket) throws IOException {
        this.commandStream = new FTPStream(socket);
        this.dataConnectionLatch = new CountDownLatch(1);
        this.fileToRenamePath = null;
    }

    /**
     * Returns the command stream.
     *
     * @return the command stream
     */
    public FTPStream getCommandStream() {
        return commandStream;
    }

    /**
     * Returns the data stream.
     *
     * @return the data stream
     */
    public FTPStream getDataStream() {
        return dataStream;
    }

    /**
     * Sets the data stream.
     *
     * @param dataStream the data stream
     */
    public void setDataStream(FTPStream dataStream) {
        this.dataStream = dataStream;
    }

    /**
     * Returns the volume.
     *
     * @return the volume
     */
    public Volume getVolume() {
        return FTPServer.getVolume();
    }

    /**
     * Sets the file to be renamed.
     *
     * @param file the file to be renamed
     */
    public void setRenameFromFilePath(String file) {
        this.fileToRenamePath = file;
    }

    /**
     * Returns the file to be renamed.
     *
     * @return the file to be renamed
     */
    public String getRenameFromFilePath() {
        return this.fileToRenamePath;
    }

    /**
     * Returns the data server socket.
     *
     * @return the data server socket
     */
    public ServerSocket getDataServerSocket() {
        return dataServerSocket;
    }

    /**
     * Sets the data server socket.
     *
     * @param dataServerSocket the data server socket
     */
    public void setDataServerSocket(ServerSocket dataServerSocket) {
        this.dataServerSocket = dataServerSocket;
    }

    /**
     * Returns the data connection latch.
     *
     * @return the data connection latch
     */
    public CountDownLatch getDataConnectionLatch() {
        return dataConnectionLatch;
    }

    /**
     * Sends a response to the client.
     *
     * @param response the response to send
     */
    public void sendResponse(String response) {
        this.commandStream.write(response);
    }

    /**
     * Sends data to the client.
     *
     * @param data the data to send
     */
    public void sendData(String data) {
        this.dataStream.write(data);
    }

    /**
     * Closes the client connection.
     */
    public void close() {
        try {
            this.commandStream.close();

            if (this.dataStream != null)
                this.dataStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes an FTP command.
     *
     * @param command the command to process
     */
    private void processCommand(String command) {
        String[] commandParts = command.split(" ");
        Command handler = FTPCommandFactory.getHandler(commandParts[0], this, commandParts);

        if (handler != null)
            handler.execute();
        else
            this.commandStream.write("Invalid command: " + command);
    }

    /**
     * Runs the FTP client.
     * Handles incoming commands from the client.
     */
    @Override
    public void run() {
        try {
            String command;

            this.commandStream.write(FTPResponseCode.WELCOME.toString());

            while ((command = this.commandStream.read()) != null) {
                this.processCommand(command);
            }
        } catch (SocketException e) {
            try {
                this.commandStream.close();
                if (this.dataStream != null) {
                    this.dataStream.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}