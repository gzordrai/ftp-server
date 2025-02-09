package com.ftp.server.ftp.commands;

/**
 * This interface represents a command that can be executed.
 * Implementations of this interface should encapsulate the logic
 * for a specific FTP command.
 */
public interface Command {
    /**
     * Executes the command.
     */
    void execute();
}