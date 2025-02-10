package com.ftp.server.ftp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftp.server.fs.Volume;

/**
 * This class represents an FTP server.
 * It handles incoming client connections and manages the server volume.
 */
public class FTPServer {
    private static Volume volume;
    private static final Logger logger = LogManager.getLogger(FTPServer.class);

    /**
     * Private constructor to prevent instantiation.
     */
    private FTPServer() {
    }

    /**
     * Starts the FTP server on the specified port and with the specified root path.
     *
     * @param port     the port to start the server on
     * @param rootPath the root path of the server volume
     * @throws IOException if an I/O error occurs
     */
    public static void start(int port, String rootPath) throws IOException {
        volume = new Volume(rootPath);

        try (ServerSocket server = new ServerSocket(port)) {
            boolean running = true;

            while (running) {
                Socket socket = server.accept();
                logger.info("New connection from {}", socket.getInetAddress().getHostAddress());
                FTPClient client = new FTPClient(socket);

                new Thread(client).start();

                if (Files.exists(Paths.get(rootPath, "stop.txt"))) {
                    logger.info("Stop signal received. Shutting down the server.");
                    running = false;
                }
            }
        }
    }

    /**
     * Returns the server volume.
     *
     * @return the server volume
     */
    public static Volume getVolume() {
        return volume;
    }
}