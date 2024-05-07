package com.information_retrieval.ir_project.algorithms;

import com.information_retrieval.ir_project.Directory;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class IndexesFactory {
    private static Map<String, List<Boolean>> incidenceMatrix;

    private static Map<String, List<Integer>> invertedIndex;

    private static Map<String, Map<Integer, List<Integer>>> positionalindex;

    private static Map<String, Set<Integer>> biwordIndex;

    public static Map<String, Set<Integer>> getBywordIndex(){
        if(biwordIndex!=null){
            return biwordIndex;
        }else{
            try{
                biwordIndex=IndexesRepository.loadBywordIndexFromFile(Directory.ARCHIVE_PATH + "/biword.txt");
            }catch (Exception ex){
                System.out.println(ex);
            }
        }
        return biwordIndex;
    }

    public static void setByWordIndex(String [] documents){
        biwordIndex= BywordIndex.buildIndex(documents);
        try {
            IndexesRepository.saveByWordIndexToFile(Directory.ARCHIVE_PATH + "/biword.txt", biwordIndex);
        }catch (Exception ex){
            System.out.println(ex);
        }
    }
    public static Map<String, List<Boolean>> getIncidenceMatrix() {
        if(incidenceMatrix !=null)
            return incidenceMatrix;
        try {
            incidenceMatrix = IndexesRepository.readIncidenceMatrixFromFile(Directory.ARCHIVE_PATH + "/IncidenceMatrix.txt");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return incidenceMatrix;
    }


    public static Map<String ,List<Boolean>> setIncidenceMatrix(List<List<String>>documents){
        incidenceMatrix=IncidenceMatrix.createMatrix(documents);
        try {
            IndexesRepository.writeIncidenceMatrixToFile(incidenceMatrix, Directory.ARCHIVE_PATH + "/IncidenceMatrix.txt");
        }catch (Exception ex){
            System.out.println(ex);
        }
        return incidenceMatrix;
    }
    public static Map<String, List<Integer>> getInvertedIndex() {
        if(invertedIndex !=null)
            return invertedIndex;
        try {
            invertedIndex = IndexesRepository.readInvertedIndexFromFile(Directory.ARCHIVE_PATH + "/InvertedIndex.txt");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return invertedIndex;
    }

    public static Map<String, List<Integer>> setInvertedIndex(List<List<String>>documents){
        invertedIndex=InvertedIndex.createInvertedIndex(documents);
        try{
            IndexesRepository.writeInvertedIndexToFile(invertedIndex,Directory.ARCHIVE_PATH + "/InvertedIndex.txt");
        }catch (Exception ex){
            System.out.println(ex);
        }
        return invertedIndex;
    }

    public static Map<String, Map<Integer, List<Integer>>> getPositionalIndex() {
        if(positionalindex !=null)
            return positionalindex;
        try {
            positionalindex = IndexesRepository.readIndexFromFile(Directory.ARCHIVE_PATH + "/positionalIndex.txt");
        } catch (Exception ex) {
            System.out.println("read excption");
        }
        return positionalindex;
    }

    public static Map<String, Map<Integer, List<Integer>>> setPositionalIndex(List<List<String>>documents){
        positionalindex = positionalIndex.positionalIndexAlgorithm(documents);
        try{
            IndexesRepository.writepositionalIndexFromFile(positionalindex,Directory.ARCHIVE_PATH + "/positionalIndex.txt");
        }catch (Exception ex){
            System.out.println(ex);
        }
        return positionalindex;
    }



}
