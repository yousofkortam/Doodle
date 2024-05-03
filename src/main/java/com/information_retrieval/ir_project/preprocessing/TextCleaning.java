package com.information_retrieval.ir_project.preprocessing;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TextCleaning {



    public ArrayList<String> Tokenization(String doc) {

        if (doc.matches(".*\\w\\..*")) {
            doc = doc.toLowerCase().replaceAll("(\\w)\\.", "$1");
        }
        String[] tokens = doc.toLowerCase().split("[\\s.;,()/:'?!$*+#^\"]+");
        ArrayList<String> tokenWord = new ArrayList<>(Arrays.asList(tokens));
        return tokenWord;

    }

    public ArrayList<String> stopWords(ArrayList<String> tokens) {

        Set<String> stopWords = new HashSet<String>();
        stopWords.addAll(Arrays.asList(new String[]{"ourselves",
            "hers", "between", "yourself", "but", "again", "there", "about", "once",
            "during", "out", "very", "having", "with", "they", "own", "an", "be",
            "some", "for", "do", "its", "yours", "such", "into", "of", "most", "itself",
            "other", "off", "is", "s", "am", "or", "who", "as", "from", "him", "each",
            "the", "themselves", "until", "below", "are", "we", "these", "your", "his",
            "through", "don", "nor", "me", "were", "her", "more", "himself", "this", "down",
            "should", "our", "their", "while", "above", "both", "up", "to", "ours", "had",
            "she", "all", "no", "when", "at", "any", "before", "them", "same", "and",
            "been", "have", "in", "will", "on", "does", "yourselves", "then", "that",
            "because", "what", "over", "why", "so", "can", "did", "not", "now", "under",
            "he", "you", "herself", "has", "just", "where", "too", "only", "myself",
            "which", "those", "i", "after", "few", "whom", "t", "being", "if", "theirs", "my",
            "against", "a", "by", "doing", "it", "how", "further", "was", "here", "than","O","\n"," "}));
        ArrayList<String> newTokenz = new ArrayList<>();

        for (String words : tokens) {
            if (!stopWords.contains(words)) {
                newTokenz.add(words);
            }
        }

        return newTokenz;
    }

    public ArrayList<String> normailzation(ArrayList<String> input) {
        ArrayList<String> normTokenz = new ArrayList<String>();
        for (String token : input) {
            if (token.matches(".*\\w\\..*")) {
                token = token.toLowerCase().replaceAll("(\\w)\\.", "$1");
            }
            normTokenz.add(Normalizer.normalize(token, Normalizer.Form.NFD)
                    .replaceAll("\\p{M}", "").toLowerCase());
        }
        return normTokenz;
    }
}
