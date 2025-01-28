package com.ftp.server.ftp;

import java.io.IOException;
import java.net.Socket;

public class FTPClient implements Runnable {
    private FTPStream commandStream;
    private FTPStream dataStream;

    public FTPClient(Socket socket) throws IOException {
        this.commandStream = new FTPStream(socket);
    }

    private void processCommand(String command) {
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
                    this.commandStream.write(FTPResponseCode.ENTERING_PASSIVE_MODE.getMessage("127,0,0,1,0,0"));
                    break;

                case LIST:
                    System.out.println("List command");
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
            System.out.println("Invalid commandO: " + command);
        }
    }

    public void run() {
        try {
            String command;

            this.commandStream.write(FTPResponseCode.WELCOME);

            while ((command = this.dataStream.read()) != null) {
                this.processCommand(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
