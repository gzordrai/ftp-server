package com.ftp.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftp.server.ftp.FTPServer;

/**
 * The main class for starting the FTP server.
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    /**
     * The main method for starting the FTP server.
     *
     * @param args the command line arguments
     *             args[0] - the port to start the server on
     *             args[1] - the root path of the server volume
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            logger.error("Usage: java -jar ftp-server.jar <port> <volume>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        String rootPath = args[1];

        logger.info("Starting FTP server on port {} with volume path {}", port, rootPath);

        if (!Files.exists(Paths.get(rootPath))) {
            logger.error("Error: The specified volume path does not exist.");
            System.exit(1);
        }

        FTPServer.start(port, rootPath);
    }
}