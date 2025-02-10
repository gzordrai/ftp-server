package com.ftp.server.ftp;

import com.ftp.server.ftp.commands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Factory class for creating FTP command handlers.
 */
public class FTPCommandFactory {
    private static final Map<String, Class<? extends Command>> commandHandlers = new HashMap<>();
    private static final Logger logger = LogManager.getLogger(FTPCommandFactory.class);

    /**
     * Private constructor to prevent instantiation.
     */
    private FTPCommandFactory() {
    }

    static {
        commandHandlers.put("AUTH", AuthCommand.class);
        commandHandlers.put("USER", UserCommand.class);
        commandHandlers.put("PASS", PassCommand.class);
        commandHandlers.put("SYST", SystCommand.class);
        commandHandlers.put("FEAT", FeatCommand.class);
        commandHandlers.put("PWD", PwdCommand.class);
        commandHandlers.put("TYPE", TypeCommand.class);
        commandHandlers.put("PASV", PasvCommand.class);
        commandHandlers.put("EPSV", EpsvCommand.class);
        commandHandlers.put("LIST", ListCommand.class);
        commandHandlers.put("CWD", CwdCommand.class);
        commandHandlers.put("CDUP", CdupCommand.class);
        // commandHandlers.put("RETR", RetrCommand.class);
        // commandHandlers.put("STOR", StorCommand.class);
        commandHandlers.put("RNFR", RnfrCommand.class);
        commandHandlers.put("RNTO", RntoCommand.class);
        commandHandlers.put("QUIT", QuitCommand.class);
    }

    /**
     * Returns the command handler for the specified command.
     *
     * @param command the command name
     * @param client the FTP client
     * @param args the command arguments
     * @return the command handler, or null if no handler is found
     */
    public static Command getHandler(String command, FTPClient client, String[] args) {
        Class<? extends Command> handlerClass = commandHandlers.get(command.toUpperCase());

        logger.info("Command: {} from client: {}", command, "");

        if (handlerClass != null) {
            try {
                if (args.length > 1)
                    return handlerClass.getDeclaredConstructor(FTPClient.class, String[].class).newInstance(client, args);
                else
                    return handlerClass.getDeclaredConstructor(FTPClient.class).newInstance(client);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Returns the set of all command handlers.
     *
     * @return the set of all command handlers
     */
    public static Set<String> getCommandHandlers() {
        return commandHandlers.keySet();
    }
}