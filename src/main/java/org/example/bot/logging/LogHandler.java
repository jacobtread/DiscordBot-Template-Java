package org.example.bot.logging;

import java.io.PrintStream;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogHandler extends Handler {

    // Console colors
    public static final String RED = "\u001b[31m";
    public static final String RESET = "\u001b[0m";
    private static final String YELLOW = "\u001b[33m";
    private static final String GREEN = "\u001b[32m";
    // The log formatter
    private final Formatter formatter = new LogFormatter();

    @Override
    public void publish(LogRecord logRecord) {
        // The formatted log text
        String text = formatter.format(logRecord);
        PrintStream out;
        Level level = logRecord.getLevel();
        if (Level.SEVERE.equals(level)) {
            // Set the print stream to the system error stream
            out = System.err;
            // Apply a red color to the text
            text = RED + text + RESET;
            // Print to the error stream
            out.print(text);
            // Exit the application with error code
            System.exit(1);
        } else {
            // Set the print stream to the system out stream
            out = System.out;
        }
        if (Level.WARNING.equals(level)) {
            // Apply a yellow color to the text
            text = YELLOW + text + RESET;
        }
        if (Level.INFO.equals(level)) {
            // Apply a green color to the text
            text = GREEN + text + RESET;
        }
        out.print(text);
    }

    @Override
    public void flush() {
        // Flush both streams
        System.out.flush();
        System.err.flush();
    }

    @Override
    public void close() throws SecurityException {

    }

}