package com.information_retrieval.ir_project.preprocessing;

import java.util.ArrayList;


public class Preprocessing {

    public static ArrayList<String> preprocess(String str,
            boolean stopWords, boolean normalization, boolean Stemming, boolean lemma) throws Exception {
        TextCleaning textCleaning = new TextCleaning();
        ArrayList<String> tokens = textCleaning.Tokenization(str);
//        System.out.println("String before token : " + str);
        System.out.println("=======>String after token: " + tokens);
        if(stopWords) {
            tokens = textCleaning.stopWords(tokens);
            System.out.println("======>Stop Words :" + tokens);
        }

        if (normalization) {
            tokens = textCleaning.normailzation(tokens);
            System.out.println("=========>Normalization :" + tokens);
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
            System.out.println("Steaming : " + tokens);
        }
        if(lemma){
             tokens=StemLemmatize.lemmatizeTokens(tokens);
            System.out.println("========>Lemmatization : " + tokens);
        }
        tokens.removeIf((String x)->x.equals("O"));
        return tokens;
    }

}

