package com.graphVisualizer.utils;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

    private static final Properties properties = new Properties();

    static{
        try{
            properties.load(ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configs.\n" + e);
        }
    }

    public static String get(String key){
        return properties.getProperty(key).trim();
    }

    public static int getInt(String key){
        return Integer.parseInt(properties.getProperty(key).trim());
    }

    public static double getDouble(String key){
        return Double.parseDouble(properties.getProperty(key).trim());
    }

    public static Color getColor(String key){
        String[] color = properties.getProperty(key).trim().split(",");
        return new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2]));
    }

    public static Dimension getDim(String key){
        String[] dim = properties.getProperty(key).trim().split(",");
        return new Dimension(Integer.parseInt(dim[0]), Integer.parseInt(dim[1]));
    }

}
