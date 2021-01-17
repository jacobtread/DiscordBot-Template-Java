package org.example.bot;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Properties {

    // The stored properties
    private final Map<String, String> values = new HashMap<>();

    /**
     * Loads a properties instance from a file
     *
     * @param file The file to load the properties from
     * @return A properties instance containing the loaded properties
     * @throws IOException An IOException that could occur when loading the file
     */
    public static Properties load(Path file) throws IOException {
        if (!Files.exists(file)) {
            throw new IllegalArgumentException("The provided path does not exist!");
        } else if (Files.isDirectory(file)) {
            throw new IllegalArgumentException("The provided path was not a file!");
        } else if (!Files.isReadable(file)) {
            throw new IllegalArgumentException("Unable to read file at path!");
        }
        // Create new properties instance
        Properties properties = new Properties();
        // Read all lines from the file
        List<String> lines = Files.readAllLines(file);
        for (int lineIndex = 0, linesSize = lines.size(); lineIndex < linesSize; lineIndex++) {
            String line = lines.get(lineIndex);
            // Make sure the line is not a comment line and is not blank
            if (line.startsWith("#") || line.isBlank()) {
                continue;
            }
            int length = line.length();
            int equalsIndex = line.indexOf('=');
            // Check if the line contains an equals sign
            if (equalsIndex == -1) {
                // Throw an error informing the user that there is an invalid line and where it is
                throw new InvalidPropertyException(String.format("Invalid line in properties file at line %d: %s <-- Missing equals and value", lineIndex, line));
            } else {
                // Get the key which is all the text before the equals sign
                String key = line.substring(0, equalsIndex);
                // Get the value which is everything after the equals sign (if nothing is there then null)
                String value = length <= equalsIndex ? null : line.substring(equalsIndex + 1);
                // Store the property value
                properties.put(key, value);
            }
        }
        return properties;
    }

    /**
     * Saves the properties to a file
     *
     * @param file The file to save the properties to
     * @throws IOException An IOException that could occur when saving the file
     */
    public void save(Path file) throws IOException {
        if (!Files.exists(file)) {
            Files.createFile(file);
        }
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(file)) {
            for (Map.Entry<String, String> property : values.entrySet()) {
                String value = property.getValue();
                bufferedWriter.write(property.getKey() + "=" + (value == null ? "" : value));
                bufferedWriter.newLine();
            }
        }
    }

    /**
     * Sets a property value
     *
     * @param key The key to use
     * @param value The value to set
     */
    public void put(String key, String value) {
        values.put(key, value);
    }

    /**
     * Gets a property value or null;
     *
     * @param key The key to search for
     * @return The value that was found or null
     */
    public String get(String key) {
        return values.getOrDefault(key, null);
    }

    /**
     * Gets a property value or the default provided
     *
     * @param key The key to search for
     * @param defaultValue The value to return if the key isn't found
     * @return The value that was found
     */
    public String getOrDefault(String key, String defaultValue) {
        return values.getOrDefault(key, defaultValue);
    }

    /**
     * Check if a property is loaded
     *
     * @param key The key to search for
     * @return Whether or not the property exists
     */
    public boolean has(String key) {
        return values.containsKey(key);
    }

    @Override
    public String toString() {
        return "Properties" + values;
    }

    public static class InvalidPropertyException extends RuntimeException {

        public InvalidPropertyException(String message) {
            super(message);
        }

    }
}
