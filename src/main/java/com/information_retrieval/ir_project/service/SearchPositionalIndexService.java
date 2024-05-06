package com.information_retrieval.ir_project.service;

import com.information_retrieval.ir_project.algorithms.IndexesFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SearchPositionalIndexService implements SearchService {

    private final DivideToTokensService divideToTokensService;
    private final SearchingFiltersService searchingFiltersService;

    public ArrayList<Integer> SearchYaLeazy(String Token) {
        var matrix = IndexesFactory.getPositionalIndex();
        ArrayList<Integer> result = new ArrayList<>();

        for (Map.Entry<String, Map<Integer, List<Integer>>> entry : matrix.entrySet()) {
            String key = entry.getKey();
            Map<Integer, List<Integer>> innerMap = entry.getValue();
            if (Token.equals(key)) {
                for (Map.Entry<Integer, List<Integer>> innerEntry : innerMap.entrySet()) {
                    Integer innerKey = innerEntry.getKey();
                    result.add(innerKey);
                }
            }
        }
        return result;
    }

    public List<Integer> searchPhrase(String query) {
        Map<String, Map<Integer, List<Integer>>> positionalIndex = IndexesFactory.getPositionalIndex();
        String[] terms = query.split(" ");
        List<Integer> result = new ArrayList<>();

        if (terms.length == 0) {
            return result;
        }

        Map<Integer, List<Integer>> firstTermPositions = positionalIndex.getOrDefault(terms[0], Collections.emptyMap());
        for (Integer docId : firstTermPositions.keySet()) {
            List<Integer> positions = firstTermPositions.get(docId);
            for (Integer pos : positions) {
                boolean found = true;
                for (int i = 1; i < terms.length; i++) {
                    Map<Integer, List<Integer>> nextTermPositions = positionalIndex.getOrDefault(terms[i], Collections.emptyMap());
                    List<Integer> nextPositions = nextTermPositions.getOrDefault(docId, Collections.emptyList());
                    if (!nextPositions.contains(pos + i)) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    result.add(docId);
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public List<Integer> Search(String SearchText, boolean Normalization, boolean Steaming, boolean Lemetization) {
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
            if( words.size() > 2 ){
                ans = searchPhrase( SearchText );
            }
            else if (!AND && !OR) {
                ans = SearchYaLeazy(words.get(0));
            } else if (AND) {
                // AND Operation ;
                var ret1 = SearchYaLeazy(words.get(0));
                var ret2 = SearchYaLeazy(words.get(1));

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
                var ret1 = SearchYaLeazy(words.get(0));
                var ret2 = SearchYaLeazy(words.get(1));

                ans = new ArrayList<>(ret1);
                for (Integer element : ret2) { // Iterate over ret2
                    if (!ans.contains(element)) { // Check if element is not already present in the mergedList
                        ans.add(element); // Add element to the mergedList
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("error in fetching data: \n" + e);
            throw new RuntimeException(e);
        }

        return ans;
    }
}
