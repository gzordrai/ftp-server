package com.ftp.server;

import java.io.IOException;

import com.ftp.server.ftp.FTPServer;

/**
 * Hello world!
 *
 */
public class Main {
    public static void main(String[] args) throws IOException {
        FTPServer.start();
    }
}
