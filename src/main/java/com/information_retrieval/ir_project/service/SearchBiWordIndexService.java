package com.information_retrieval.ir_project.service;

import com.information_retrieval.ir_project.algorithms.BywordIndex;
import com.information_retrieval.ir_project.algorithms.IndexesFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SearchBiWordIndexService {

    private final DivideToTokensService divideToTokesService;
    public boolean AND = false, OR = false;
    public int star = -1;

    public List<Integer> SearchBiWord(String Token) {
        var allToken = IndexesFactory.getBywordIndex();
        return BywordIndex.search( Token , allToken );
    }

    public List<Integer> SearchBiWordStart(String Token) {
        var allToken = IndexesFactory.getBywordIndex();
        return BywordIndex.searchStar( Token , allToken );
    }

    public List<Integer> SearchBiWordEnd(String Token) {
        var allToken = IndexesFactory.getBywordIndex();
        return BywordIndex.searchEndWith( Token , allToken );
    }

    public void AndOr( String SearchText ){
        for (int i = 0; i < SearchText.length(); i++) {
            if (SearchText.charAt(i) == '&') AND = true;
            if (SearchText.charAt(i) == '|') OR = true;
            if (SearchText.charAt(i) == '*') star = i;
        }
    }

    public List<Integer> Search(String SearchText){

        AND = false; OR = false;  star = -1;
        AndOr(SearchText);
        List<Integer> ans = new ArrayList<>();

        try {
            System.out.println(SearchText);
            List<String> words = divideToTokesService.Tokens(SearchText);
//            var words = SearchingFilters.FilterHere( Words );

            System.out.println("words in Token equal = " + words.size() );
            for (String word : words) {
                System.out.println(word);
            }

            if( star != -1 ){
                if( star == words.get(0).length()  && words.size() == 1 ) {
                    ans = SearchBiWordStart(words.get(0));
                } else if ( star == 0 ) {
                    ans = SearchBiWordEnd(words.get(0));
                }else{
                    var ret1 = SearchBiWordStart(words.get(0));
                    var ret2 = SearchBiWordEnd(words.get(1));

                    Set<Integer> uniqueElements = new LinkedHashSet<>();

                    for (Integer value : ret1) {
                        for (Integer integer : ret2) {
                            if (Objects.equals(value, integer)) {
                                uniqueElements.add(value);
                                break;
                            }
                        }
                    }

                    ans.addAll(uniqueElements);

                }
            } else if (!AND && !OR) {
                String BiWord = words.get(0) + " " + words.get(1) ;
                ans = SearchBiWord(BiWord);
            } else if (AND) {

                String BiWord1 = words.get(0) + " " + words.get(1);
                String BiWord2 = words.get(2) + " " + words.get(3);
                // AND Operation ;
                var ret1 = SearchBiWord(BiWord1);
                var ret2 = SearchBiWord(BiWord2);

                for (Integer integer : ret1) {
                    for (Integer value : ret2) {
                        if (Objects.equals(integer, value)) {
                            ans.add(integer);
                            break;
                        }
                    }
                }

            } else {
                // OR Operation
                String BiWord1 = words.get(0) + " " + words.get(1);
                String BiWord2 = words.get(2) + " " + words.get(3);

                var ret1 = SearchBiWord(BiWord1);
                var ret2 = SearchBiWord(BiWord2);

                ans = new ArrayList<>(ret1);
                for (Integer element : ret2) { // Iterate over ret2
                    if (!ans.contains(element)) { // Check if element is not already present in the mergedList
                        ans.add(element); // Add element to the mergedList
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error in fetching data: \n" + e);
            throw new RuntimeException(e);
        }

        return ans ;
    }

}
