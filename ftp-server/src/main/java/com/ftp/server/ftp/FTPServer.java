package com.ftp.server.ftp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer {
    public static void start(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);

        while (true) {
            Socket socket = server.accept();
            System.out.println("New connection from " + socket.getInetAddress().getHostAddress());
            FTPClient client = new FTPClient(socket);

            client.run();
        }
    }
}
