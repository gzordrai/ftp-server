package com.ftp.server.ftp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.ftp.server.fs.Volume;

public class FTPServer {
    private static Volume volume;

    public static void start(int port, String rootPath) throws IOException {
        volume = new Volume(rootPath);
        ServerSocket server = new ServerSocket(port);

        while (true) {
            Socket socket = server.accept();
            System.out.println("New connection from " + socket.getInetAddress().getHostAddress());
            FTPClient client = new FTPClient(socket);

            new Thread(client).start();
        }
    }

    public static Volume getVolume() {
        return volume;
    }
}
