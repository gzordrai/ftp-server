package com.ftp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class Main {
    private static ServerSocket server;
    private static int port = 21;

    public static void main(String[] args) throws IOException {
        server = new ServerSocket(port);

        while (true) {
            Socket socket = server.accept();

        }
    }
}
