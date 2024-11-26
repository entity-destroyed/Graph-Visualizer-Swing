package com.graphVisualizer.utils;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigLoader is a utility class for loading and accessing configuration properties from a file.
 * The properties are expected to be in a file named "config.properties" which should be available
 * in the classpath.
 * <p>
 * This class provides methods to retrieve configuration values as different types:
 * {@code String}, {@code int}, {@code double}, {@code Color}, and {@code Dimension}.
 * <p>
 * Configuration values are loaded once, during the class initialization.
 * If the properties file is not found or there is an issue during loading,
 * the class will throw a {@code RuntimeException}.
 *
 * @see RuntimeException
 */
public class ConfigLoader {

    /**
     * A static singleton instance of Properties used to store configuration properties.
     * The properties are loaded from a file named "config.properties" present in the classpath.
     * This instance is initialized only once during the class loading.
     * <p>
     * If the properties file is not found or there is an error in loading, a {@code RuntimeException} is thrown.
     */
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configs.\n" + e);
        }
    }

    /**
     * Retrieves the value associated with the specified key from the loaded configuration properties.
     * The value is trimmed of any leading and trailing whitespace.
     *
     * @param key the key for which the configuration value is to be retrieved
     * @return the trimmed configuration value associated with the specified key
     */
    public static String get(String key) {
        return properties.getProperty(key).trim();
    }

    /**
     * Retrieves an integer value corresponding to the given key from the properties.
     *
     * @param key the key whose associated integer value is to be returned
     * @return the integer value associated with the specified key
     */
    public static int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key).trim());
    }

    /**
     * Retrieves a configuration property value as a {@code double}.
     *
     * @param key the key of the property to be fetched
     * @return the double value of the property
     * @throws NumberFormatException if the property value cannot be parsed as a double
     */
    public static double getDouble(String key) {
        return Double.parseDouble(properties.getProperty(key).trim());
    }

    /**
     * Retrieves the {@code Color} value associated with the specified key from the configuration properties.
     *
     * @param key the key used to look up the color in the properties file
     * @return the Color object corresponding to the RGB values stored in the properties
     */
    public static Color getColor(String key) {
        String[] color = properties.getProperty(key).trim().split(",");
        return new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2]));
    }

    /**
     * Retrieves the {@code Dimensions} from the configuration properties file using the specified key.
     * The dimensions are read as a comma-separated string and converted into a {@code Dimension} object.
     *
     * @param key the key in the properties file whose value represents the dimensions
     * @return a {@code Dimension} object representing the width and height from the properties file.
     */
    public static Dimension getDim(String key) {
        String[] dim = properties.getProperty(key).trim().split(",");
        return new Dimension(Integer.parseInt(dim[0]), Integer.parseInt(dim[1]));
    }

}
