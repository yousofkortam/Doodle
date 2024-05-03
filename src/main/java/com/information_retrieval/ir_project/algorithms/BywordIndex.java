package com.information_retrieval.ir_project.algorithms;

import java.util.*;

public class BywordIndex {
    protected static Map<String, Set<Integer>> buildIndex(String[] documents) {
        Map<String, Set<Integer>> biwordIndex =new TreeMap<>();
        for (int i = 0; i < documents.length; i++) {
            String[] words = documents[i].toLowerCase().split("[\\s.;,()/:'?!$*+#^\"]+");
            for (int j = 0; j < words.length - 1; j++) {
                String biword = words[j] + " " + words[j + 1];
                Set<Integer> postings = biwordIndex.getOrDefault(biword, new HashSet<>());
                postings.add(i);
                biwordIndex.put(biword, postings);
            }
        }
        return biwordIndex;
    }

    public static List<Integer> search(String query,Map<String, Set<Integer>> biwordIndex) {
        String[] queryWords = query.split(" ");

        Set<Integer> result = new TreeSet<>();

        for (int i = 0; i < queryWords.length - 1; i++) {
            String biword = queryWords[i] + " " + queryWords[i + 1];
            if (!biwordIndex.containsKey(biword)) {
                return Collections.emptyList();
            }
            if (result.isEmpty()) {
                result.addAll(biwordIndex.get(biword));
            } else {
                result.retainAll(biwordIndex.get(biword));
            }
        }

        return new LinkedList<>(result);
    }

    public static List<Integer> searchStar(String query, Map<String, Set<Integer>> biwordIndex) {
        Set<Integer> result = new TreeSet<>();

        for (String biword : biwordIndex.keySet()) {
            if (biword.startsWith(query)) {
                result.addAll(biwordIndex.get(biword));
            }
        }

        return new LinkedList<>(result);
    }

    public static List<Integer> searchEndWith(String query, Map<String, Set<Integer>> biwordIndex) {
        Set<Integer> result = new TreeSet<>();

        for (String biword : biwordIndex.keySet()) {
            if (biword.endsWith(query)) {
                System.out.println(biword);
                result.addAll(biwordIndex.get(biword));
            }
        }

        return new LinkedList<>(result);
    }

    public static void main(String[] args) {
//        String[] documents = {
//                "the quick brown fox yousef here",
//                "brown fox jumps over yousef again",
//                "the lazy dog yousef here again"
//        };
//        try {
//            String[]data= ReadData.readDocuments("D:\\fourth-year\\Second Semster\\IR\\Project\\Information-Retrieval-Project\\archive\\CISI.ALL");
//            for(int i=0;i<=2;i++){
//                System.out.println("============ (" +(i+1) +")===========");
//                data[i] = data[i].toLowerCase();
//
//            }
//            IndexesFactory.setByWordIndex(data);
            var x=IndexesFactory.getBywordIndex();
//           // System.out.println("hererrrr => "+ x);
//        System.out.println("doc indexs");



        System.out.println(search("work control",x));
    }
}