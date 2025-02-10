package com.ftp.server.ftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class represents an FTP stream.
 * It handles reading from and writing to a socket.
 */
public class FTPStream {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    /**
     * Constructs a new FTPStream.
     *
     * @param socket the socket to read from and write to
     * @throws IOException if an I/O error occurs
     */
    public FTPStream(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * Reads a line of text from the socket.
     *
     * @return the line of text read from the socket
     * @throws IOException if an I/O error occurs
     */
    public String read() throws IOException {
        return this.reader.readLine();
    }

    /**
     * Writes an FTP response code to the socket.
     *
     * @param response the FTP response code to write
     */
    public void write(FTPResponseCode response) {
        this.writer.println(response.toString());
    }

    /**
     * Writes a message to the socket.
     *
     * @param message the message to write
     */
    public void write(String message) {
        this.writer.println(message);
    }

    /**
     * Closes the FTP stream.
     *
     * @throws IOException if an I/O error occurs
     */
    public void close() throws IOException {
        this.reader.close();
        this.writer.close();
    }

    /**
     * Returns the socket.
     *
     * @return the socket
     */
    public Socket getSocket() {
        return this.socket;
    }

    /**
     * Returns the input stream of the socket.
     *
     * @return the input stream of the socket
     * @throws IOException if an I/O error occurs
     */
    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }

    /**
     * Returns the output stream of the socket.
     *
     * @return the output stream of the socket
     * @throws IOException if an I/O error occurs
     */
    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }
}