package com.information_retrieval.ir_project.algorithms;

import java.io.*;
import java.util.*;

public class IndexesRepository {

    public static void writeInvertedIndexToFile(Map<String, List<Integer>> invertedIndex, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
            for (Map.Entry<String, List<Integer>> entry : invertedIndex.entrySet()) {
                writer.print(entry.getKey() + ": ");
                List<Integer> documentIndices = entry.getValue();
                for (int i = 0; i < documentIndices.size(); i++) {
                    writer.print(documentIndices.get(i));
                    if (i < documentIndices.size() - 1) {
                        writer.print(", ");
                    }
                }
                writer.println();
            }
        }
    }

    public static Map<String, List<Integer>> readInvertedIndexFromFile(String filePath) throws IOException {
        Map<String, List<Integer>> invertedIndex = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                String term = parts[0];
                List<Integer> documentIndices = new ArrayList<>();
                if (parts.length > 1) {
                    String[] indices = parts[1].split(", ");
                    for (String index : indices) {
                        documentIndices.add(Integer.parseInt(index));
                    }
                }
                invertedIndex.put(term, documentIndices);
            }
        }
        return invertedIndex;
    }

    public static void writeIncidenceMatrixToFile(Map<String, List<Boolean>> invertedIndex, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
            for (Map.Entry<String, List<Boolean>> entry : invertedIndex.entrySet()) {
                writer.print(entry.getKey() + ": ");
                List<Boolean> documentFlags = entry.getValue();
                for (int i = 0; i < documentFlags.size(); i++) {
                    writer.print(documentFlags.get(i));
                    if (i < documentFlags.size() - 1) {
                        writer.print(", ");
                    }
                }
                writer.println();
            }
        }
    }

    public static Map<String, List<Boolean>> readIncidenceMatrixFromFile(String filePath) throws IOException {
        Map<String, List<Boolean>> invertedIndex = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                String term = parts[0];
                List<Boolean> documentFlags = new ArrayList<>();
                if (parts.length > 1) {
                    String[] flags = parts[1].split(", ");
                    for (String flag : flags) {
                        documentFlags.add(Boolean.parseBoolean(flag));
                    }
                }
                invertedIndex.put(term, documentFlags);
            }
        }
        return invertedIndex;
    }

    public static void writepositionalIndexFromFile(Map<String, Map<Integer, List<Integer>>> postionalIndex, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Map.Entry<String, Map<Integer, List<Integer>>> entry : postionalIndex.entrySet()) {
                String term = entry.getKey();
                Map<Integer, List<Integer>> postings = entry.getValue();
                writer.write(term + ":- ");
                for (Map.Entry<Integer, List<Integer>> posting : postings.entrySet()) {
                    int docId = posting.getKey();
                    List<Integer> positions = posting.getValue();
                    writer.write( docId + ": " + positions.toString() + " , ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Map<Integer, List<Integer>>> readIndexFromFile(String filePath) throws IOException {
        Map<String, Map<Integer, List<Integer>>> map = new TreeMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(":-\\s+");
            String term = tokens[0];
            String[] postings = tokens[1].split("\\s,\\s");
            for (String posting : postings) {
                String[] docParts = posting.trim().split(":\\s*");
                int docid = Integer.parseInt(docParts[0]);
                String[] posParts = docParts[1].replaceAll("[\\[\\]]", "").split(",\\s+");
                List<Integer> positions = new ArrayList<>();
                for (int i = 0; i < posParts.length; i++) {
                    positions.add(Integer.parseInt(posParts[i]));
                }
                Map<Integer, List<Integer>> postingsMap = map.computeIfAbsent(term, k -> new TreeMap<>());
                postingsMap.put(docid, positions);
            }
        }
        reader.close();
        //System.out.println(map);
        return map;
    }


    public static void saveByWordIndexToFile(String filename,Map<String, Set<Integer>> biwordIndex) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(biwordIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Set<Integer>> loadBywordIndexFromFile(String filename) {
        Map<String, Set<Integer>> biwordIndex=null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            biwordIndex = (Map<String, Set<Integer>>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return biwordIndex;
    }
}
