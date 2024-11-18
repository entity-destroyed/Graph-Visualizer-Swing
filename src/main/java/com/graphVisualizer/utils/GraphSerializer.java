package com.graphVisualizer.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GraphSerializer {
    private static final String FILE_PATH = "data/data.txt";
    public static void writeGraphsToFile(List<String> lines){
        File outputFile = new File(FILE_PATH);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(String.join("\n", lines));
            writer.close();
            System.out.println("Data written to file successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static List<String> readGraphsFromFile(){
        File inputFile = new File(FILE_PATH);
        List<String> inputs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while((line = reader.readLine()) != null)
                inputs.add(line);
            reader.close();
            System.out.println("Data written to file successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
        return inputs;
    }
}
