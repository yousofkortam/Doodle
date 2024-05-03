package com.information_retrieval.ir_project.preprocessing;

import java.util.ArrayList;


public class Preprocessing {

    public static ArrayList<String> preprocess(String str,
            boolean stopWords, boolean normalization, boolean Stemming, boolean lemma) throws Exception {
        TextCleaning textCleaning = new TextCleaning();
        ArrayList<String> tokens = textCleaning.Tokenization(str);
        if(stopWords)
            tokens=textCleaning.stopWords(tokens);
        if (normalization) {
            tokens = textCleaning.normailzation(tokens);
        }
        if(Stemming){
            PorterStemmer stemmer = new PorterStemmer();
            ArrayList<String> stemOut=new ArrayList<>();
          for(String words  : tokens){
               stemmer.add(words.toCharArray(), words.length());
               stemmer.stem();
               String out = new String(stemmer.getResultBuffer(), 0, stemmer.getResultLength());
               stemOut.add(out);
          }
          tokens=stemOut;
        }
        if(lemma){
             tokens=StemLemmatize.lemmatizeTokens(tokens);
        }
        tokens.removeIf((String x)->x.equals("O"));
        return tokens;
    }

    /*
     * Tokenization ✅
     * StopWords ✅
     * Normalization ✅
     * Stemming ✅
     * Lemma ✅
     * */
}

