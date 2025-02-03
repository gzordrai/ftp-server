package com.ftp.server.ftp;

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

    public FTPClient(Socket socket) throws IOException {
        this.commandStream = new FTPStream(socket);
    }

    private void processCommand(String command) throws IOException, InterruptedException {
        try {
            String[] commandParts = command.split(" ");
            FTPCommand ftpCommand = FTPCommand.valueOf(commandParts[0].toUpperCase());

            switch (ftpCommand) {
                case AUTH:
                    this.commandStream.write(FTPResponseCode.LOGIN_WITH_USER_PASS);
                    break;

                case USER:
                    this.commandStream.write(FTPResponseCode.SPECIFY_PASSWORD);
                    break;

                case PASS:
                    this.commandStream.write(FTPResponseCode.LOGIN_SUCCESSFUL);
                    break;

                case SYST:
                    StringBuilder commands = new StringBuilder();

                    for (FTPCommand c : FTPCommand.values()) {
                        commands.append(c.name()).append("\n");
                    }

                    this.commandStream.write(commands.toString());
                    break;

                case PWD:
                    String currentDirectory = "/";
                    this.commandStream.write(FTPResponseCode.CURRENT_DIRECTORY.getMessage(currentDirectory));
                    break;

                case TYPE:
                    this.commandStream.write(FTPResponseCode.SWITCHING_TO_BINARY);
                    break;

                case PASV:
                    handlePASV();
                    break;
                case LIST:
                    handleList();
                    break;
                case CWD:
                    System.out.println("Cwd command");
                    break;
                case RETR:
                    System.out.println("Retr command");
                    break;
                case STOR:
                    System.out.println("Stor command");
                    break;
                case QUIT:
                    System.out.println("Quit command");
                    break;
                default:
                    System.out.println("Invalid command: " + command);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid command: " + command);
        }
    }

    private void handlePASV() throws IOException {
        this.dataConnectionLatch = new CountDownLatch(1);

        if (dataServerSocket != null && !dataServerSocket.isClosed()) {
            dataServerSocket.close();
        }

        dataServerSocket = new ServerSocket(0); // Bind to any available port
        int port = dataServerSocket.getLocalPort();
        String hostAddress = commandStream.getSocket().getLocalAddress().getHostAddress().replace('.', ',');

        String response = String.format("227 Entering Passive Mode (%s,%d,%d)", hostAddress, port / 256, port % 256);
        this.commandStream.write(response);

        try {
            Socket dataSocket = dataServerSocket.accept();
            this.dataStream = new FTPStream(dataSocket);
            this.dataConnectionLatch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleList() throws IOException, InterruptedException {
        this.commandStream.write(FTPResponseCode.TRANSFER_START);
        StringBuilder files = new StringBuilder();

        for (File f : FTPServer.getVolume().listFiles()) {
            if (f.isDirectory()) {
                files.append("drwxr-xr-x 1 owner group ").append(f.length()).append(" Jan 1 00:00 ")
                        .append(f.getName()).append("\r\n");
            } else {
                files.append("-rw-r--r-- 1 owner group ").append(f.length()).append(" Jan 1 00:00 ")
                        .append(f.getName()).append("\r\n");
            }
        }

        if (dataConnectionLatch != null)
            dataConnectionLatch.await();

        if (dataStream != null) {
            this.dataStream.write(files.toString());
            this.dataStream.close();
            this.dataStream = null;
        } else {
            this.commandStream.write("425 Can't open data connection.");
        }

        this.commandStream.write(FTPResponseCode.DIRECTORY_SENDED);
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
                this.dataStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
