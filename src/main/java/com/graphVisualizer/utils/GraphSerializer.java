package com.graphVisualizer.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GraphSerializer is responsible for serializing and deserializing graph data to and from a file.
 * The file path used for storage is retrieved from the configuration properties.
 */
public class GraphSerializer {
    /**
     * The file path used for storing or retrieving graph data files.
     * The value is loaded from the configuration properties using the key "data.filePath".
     */
    private static final String FILE_PATH = ConfigLoader.get("data.filePath");

    /**
     * Writes the given list of graph data strings to a file specified by the configuration.
     *
     * @param lines the list of graph data strings to be written to the file.
     */
    public static void writeGraphsToFile(List<String> lines){
        File outputFile = new File(FILE_PATH);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(String.join("\n", lines));
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Reads graph data from a file specified by the configuration and returns it as a list of strings.
     *
     * @return a list of strings, each representing a line of graph data read from the file
     */
    public static List<String> readGraphsFromFile(){
        File inputFile = new File(FILE_PATH);
        List<String> inputs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while((line = reader.readLine()) != null)
                inputs.add(line);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
        return inputs;
    }
}
