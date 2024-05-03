package com.information_retrieval.ir_project.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class positionalIndex {
    public static Map<String, Map<Integer, List<Integer>>> positionalIndexAlgorithm(List<List<String>> documents) {
        Map<String, Map<Integer, List<Integer>>> index = new TreeMap<>();
        Map<String, Integer> docFreq = new TreeMap<>();
        int docId = 0;
            for (var doc : documents) {
                int pos = 0;
                for (var term : doc) {
                    if (!index.containsKey(term)) {
                        index.put(term, new TreeMap<>());
                    }
                    Map<Integer, List<Integer>> postings = index.get(term);
                    if (!postings.containsKey(docId)) {
                        postings.put(docId, new ArrayList<>());
                        docFreq.put(term, docFreq.getOrDefault(term, 0) + 1);
                    }
                    List<Integer> positions = postings.get(docId);
                    positions.add(pos);
                    pos++;
                }
                docId++;
            }
            return index;
    }
}