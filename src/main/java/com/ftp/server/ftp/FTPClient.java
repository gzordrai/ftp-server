package com.ftp.server.ftp;

import com.ftp.server.fs.Volume;
import com.ftp.server.ftp.commands.Command;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CountDownLatch;

public class FTPClient implements Runnable {
    private FTPStream commandStream;
    private FTPStream dataStream;
    private ServerSocket dataServerSocket;
    private CountDownLatch dataConnectionLatch;
    private String fileToRenamePath;

    public FTPClient(Socket socket) throws IOException {
        this.commandStream = new FTPStream(socket);
        this.dataConnectionLatch = new CountDownLatch(1);
        this.fileToRenamePath = null;
    }

    public FTPStream getCommandStream() {
        return commandStream;
    }

    public FTPStream getDataStream() {
        return dataStream;
    }

    public Volume getVolume() {
        return FTPServer.getVolume();
    }

    public void setDataStream(FTPStream dataStream) {
        this.dataStream = dataStream;
    }

    public void setRenameFromFilePath(String file) {
        this.fileToRenamePath = file;
    }

    public String getRenameFromFilePath() {
        return this.fileToRenamePath;
    }

    public ServerSocket getDataServerSocket() {
        return dataServerSocket;
    }

    public void setDataServerSocket(ServerSocket dataServerSocket) {
        this.dataServerSocket = dataServerSocket;
    }

    public CountDownLatch getDataConnectionLatch() {
        return dataConnectionLatch;
    }

    public void sendResponse(String response) {
        this.commandStream.write(response);
    }

    public void sendData(String data) {
        this.dataStream.write(data);
    }

    public void close() {
        try {
            this.commandStream.close();

            if (this.dataStream != null)
                this.dataStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processCommand(String command) {
        String[] commandParts = command.split(" ");
        Command handler = FTPCommandFactory.getHandler(commandParts[0], this, commandParts);

        if (handler != null)
            handler.execute();
        else
            this.commandStream.write("Invalid command: " + command);
    }

    public void run() {
        try {
            String command;

            this.commandStream.write(FTPResponseCode.WELCOME);

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