package com.information_retrieval.ir_project.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class IncidenceMatrix {

    public static Map<String,List<Boolean>> glopalMatrix;
    /**
     * this function take the documents as an input and return matrix if
     * word exist its value will be true else it will be false
     * number of columns is the number of documents and the size of the map is the size of
     * the terms
     * @param documents
     * @return Map<String,List<Boolean>> matrix
     */
    public static Map<String,List<Boolean>> createMatrix(List<List<String>> documents) {

        Map<String,List<Boolean>> matrix =new TreeMap<>() ;

        List<String> tokens=documents.stream().flatMap(List::stream).toList();
        for(var token:tokens){
            ArrayList<Boolean> arr=new ArrayList<>();
            for(int i=0;i<documents.size();i++){
                arr.add(false);
            }
            matrix.put(token,arr);
        }

        for(int i=0;i<documents.size();i++){
            for(var x:documents.get(i)){
                matrix.get(x).set(i,true);
            }
        }
        glopalMatrix=matrix;
        return matrix;
    }
}
