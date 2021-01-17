package org.example.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.SelfUser;
import org.example.bot.command.CommandManager;
import org.example.bot.logging.LogFormatter;
import org.example.bot.logging.LogHandler;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bot {

    // This is the path to the configuration file /config.properties
    private static final Path CONFIG_PATH = Paths.get("config.properties");
    // The bot logger
    public static final Logger LOGGER = Logger.getLogger("BotLogger");

    static {
        // Disables the default loggers
        LOGGER.setUseParentHandlers(false);
        // Add the custom console log handler
        LOGGER.addHandler(new LogHandler());
        try {
            Path logsPath = Paths.get("logs");
            // Create the logging directory if its missing /logs
            if (!Files.exists(logsPath)) {
                Files.createDirectories(logsPath);
            }
            // Date formatter (e.g 16-1-2021)
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d-M-y");
            // The file name pattern for logs (e.g logs/log-16-1-2021.txt)
            String logPattern = "logs/log-" + simpleDateFormat.format(new Date()) + ".txt";
            // The File handler
            FileHandler fileHandler = new FileHandler(logPattern, true);
            // Set the logging formatter to the custom formatter
            fileHandler.setFormatter(new LogFormatter());
            // Add the file log handler
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            // Warn the user that it failed to add the file logger
            LOGGER.log(Level.WARNING, "Unable to add file logger", e);
        }
    }

    private Properties properties; // Our properties storage
    private CommandManager commandManager; // The command manager

    /**
     * Setup the bot, Connect to discord, Load the command manager
     */
    public Bot() {
        long startTime = System.currentTimeMillis(); // The system time at startup in MS
        try {
            LOGGER.info("Loading properties file...");
            // Load the properties file
            properties = Properties.load(CONFIG_PATH);
            // Check that the properties contains a token
            if (properties.has("token")) {
                // Retrieve the token from the properties
                String token = properties.get("token");
                LOGGER.info("Connecting with token: " + token);
                // Creates JDA instance
                // Set custom activity here
                // The instance of JDA
                JDA jda = JDABuilder.createDefault(token)
                        .setActivity(Activity.watching("over the server.")) // Set custom activity here
                        .addEventListeners(commandManager)
                        .build();
                // Create and load the command manager
                commandManager = new CommandManager(this, jda);
                try {
                    SelfUser selfUser = jda.getSelfUser();
                    LOGGER.info(String.format("Successfully logged in to \"%s#%s\" ID: %s", selfUser.getName(), selfUser.getDiscriminator(), selfUser.getId()));
                    // Wait until the discord bot is fully ready
                    jda.awaitReady();
                    long endTime = System.currentTimeMillis() - startTime; // The total time taken from startup
                    LOGGER.info("Loading completed in " + endTime + "ms");
                } catch (InterruptedException ignored) {
                    LOGGER.severe("Thread interrupted while waiting for bot to become ready.");
                }
            } else {
                LOGGER.severe("Missing Bot token please provide one in the config: " + CONFIG_PATH.toAbsolutePath().toString());
            }
        } catch (IOException e) {
            // Inform the user that we couldn't load the configuration file and exits the application
            LOGGER.log(Level.SEVERE, "Failed to load configuration file", e);
        } catch (LoginException e) {
            // Inform the user that we couldn't log-in to discord and exits the application
            LOGGER.log(Level.SEVERE, "Unable to login to Discord", e);
        }

    }

    /**
     * The entry point of the application
     *
     * @param args Arguments passed to the application
     */
    public static void main(String[] args) {
        // Create new bot instance
        new Bot();
    }

    public Properties getProperties() {
        return properties;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
