package com.information_retrieval.ir_project.service;

import com.information_retrieval.ir_project.algorithms.Lucene_Searcher;
import org.apache.lucene.queryparser.classic.ParseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SearchLuceneService implements SearchService {

    private final DivideToTokensService divideToTokensService;
    private final SearchingFiltersService searchingFiltersService;


    public ArrayList<Integer> SearchLucene(String Token) throws IOException, ParseException {
        return Lucene_Searcher.searcher(Token);
    }

    @Override
    public List<Integer> Search(String SearchText, boolean Normalization, boolean Steaming, boolean Lemetization) {
        ArrayList<Integer> ans = new ArrayList<>();

        try {
            boolean AND = false, OR = false;

            for (int i = 0; i < SearchText.length(); i++) {
                if (SearchText.charAt(i) == '&') AND = true;
                if (SearchText.charAt(i) == '|') OR = true;
            }

            List<String> Words = divideToTokensService.Tokens(SearchText);

            var words = searchingFiltersService.FilterHere(Words, Normalization, Steaming, Lemetization);


            if (!AND && !OR) {
                ans = SearchLucene(words.get(0));
            } else if (OR) {
                ArrayList<Integer> ret1 = SearchLucene(words.get(0));
                ArrayList<Integer> ret2 = SearchLucene(words.get(1));

                ans.addAll(ret1);
                for (Integer element : ret2) {
                    if (!ans.contains(element)) {
                        ans.add(element);
                    }
                }
            } else {
                ArrayList<Integer> ret1 = SearchLucene(words.get(0));
                ArrayList<Integer> ret2 = SearchLucene(words.get(1));

                for (Integer integer : ret1) {
                    for (Integer value : ret2) {
                        if (Objects.equals(integer, value)) {
                            ans.add(integer);
                            break;
                        }
                    }
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ans;
    }

}
