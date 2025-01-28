package com.ftp.server;

import java.io.IOException;

import com.ftp.server.ftp.FTPServer;

/**
 * Hello world!
 *
 */
public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java -jar ftp-server.jar <port>");
            System.exit(1);
        }

        FTPServer.start(Integer.parseInt(args[0]));
    }
}
