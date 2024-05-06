package com.information_retrieval.ir_project.service;

import com.information_retrieval.ir_project.algorithms.IndexesFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SearchInvertedService implements SearchService {

    private final DivideToTokensService divideToTokensService;
    private final SearchingFiltersService searchingFiltersService;

    public List<Integer> SearchInverted(String Token) {

        List<Integer> ans = new ArrayList<>();
        Map<String, List<Integer>> matrix = IndexesFactory.getInvertedIndex();
//        Map<String, Map<Integer, List<Integer>>> matrix = null;
        if (matrix.get(Token) != null) {
            List<Integer> list = matrix.get(Token);
            ans.addAll(list);
        }
        return ans;
    }

    @Override
    public List<Integer> Search(String SearchText, boolean Normalization,  boolean Steaming, boolean Lemetization){
        List<Integer> ans = new ArrayList<>();
        boolean AND = false, OR = false;

        for (int i = 0; i < SearchText.length(); i++) {
            if (SearchText.charAt(i) == '&') AND = true;
            if (SearchText.charAt(i) == '|') OR = true;
        }

        try {
            List<String> Words = divideToTokensService.Tokens(SearchText);

            var words = searchingFiltersService.FilterHere(Words, Normalization, Steaming, Lemetization);

            System.out.println("words in Token equal = ");
            for (String word : words) {
                System.out.println(word);
            }
            System.out.println("-------------------------------------");
            if (!AND && !OR) {
                ans = SearchInverted(words.get(0));
            } else if (AND) {
                // AND Operation ;
                var ret1 = SearchInverted(words.get(0));
                var ret2 = SearchInverted(words.get(1));

                for (Integer integer : ret1) {
                    for (Integer value : ret2) {
                        if (Objects.equals(integer, value)) {
                            ans.add(integer);
                            break;
                        }
                    }
                }
            } else {
                // OR Operation ;
                var ret1 = SearchInverted(words.get(0));
                var ret2 = SearchInverted(words.get(1));

                ans = new ArrayList<>(ret1);
                for (Integer element : ret2) { // Iterate over ret2
                    if (!ans.contains(element)) { // Check if element is not already present in the mergedList
                        ans.add(element); // Add element to the mergedList
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("error in fetching data: \n" + e);
//            SearchResult = "Error in Showing Data :(";
            throw new RuntimeException(e);
        }

        return ans ;
    }

}
