package com.ftp.server.ftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class FTPStream {
    private BufferedReader reader;
    private PrintWriter writer;

    public FTPStream(Socket socket) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public String read() throws IOException {
        return this.reader.readLine();
    }

    public void write(FTPResponseCode response) {
        this.writer.println(response.toString());
    }

    public void write(String message) {
        this.writer.println(message);
    }

    public void close() throws IOException{
        this.reader.close();
        this.writer.close();
    }
}
