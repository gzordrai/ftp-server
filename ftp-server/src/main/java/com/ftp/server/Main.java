package com.ftp.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.ftp.server.ftp.FTPServer;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java -jar ftp-server.jar <port> <volume>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        String rootPath = args[1];

        if (!Files.exists(Paths.get(rootPath))) {
            System.out.println("Error: The specified volume path does not exist.");
            System.exit(1);
        }

        FTPServer.start(port, rootPath);
    }
}
