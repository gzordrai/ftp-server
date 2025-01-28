package com.ftp.server.ftp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer {
    private static final String welcomeMessage = "220 Welcome to FTP server";
    private static int port = 1996;

    public static void start() throws IOException {
        ServerSocket server = new ServerSocket(port);

        while (true) {
            Socket socket = server.accept();
            System.out.println("New connection from " + socket.getInetAddress().getHostAddress());
            FTPClient client = new FTPClient(socket);

            client.run();
        }
    }
}
