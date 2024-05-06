package com.information_retrieval.ir_project.service;

import com.information_retrieval.ir_project.preprocessing.Preprocessing;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchingFiltersService {

    public List<String> FilterHere(List<String> words, boolean Normalization,  boolean Steaming, boolean Lemetization) throws Exception {
        List<String> ans = new ArrayList<>() ;
        ArrayList<String> wordFilter;
        for(var word: words){
            wordFilter = Preprocessing.preprocess(word, false, Normalization, Steaming, Lemetization);
            System.out.println(wordFilter);
            ans.add(wordFilter.get(0));
        }
        return ans;
    }

}
