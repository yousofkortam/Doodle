package com.information_retrieval.ir_project.service.search;

import com.information_retrieval.ir_project.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchIncidenceMatrixService searchIncidenceMatrixService;
    private final SearchLuceneService searchLuceneService;
    private final SearchInvertedService searchInvertedService;
    private final SearchPositionalIndexService searchPositionalIndexService;
    private final SearchBiWordIndexService searchBiWordIndexService;

    public String search(String indexType, String searchText, boolean normalization, boolean steaming, boolean lemmatization) {
        log.info("Search parameters - Index Type: {}, Search Text: {}, Normalization: {}, Steaming: {}, Lemmatization: {}",
                indexType, searchText, normalization, steaming, lemmatization);

        searchText = searchText.trim().toLowerCase();

        List<Integer> searchResult = new ArrayList<>();

        try {
            if (indexType.equals("Incidence-matrix")) {
                searchResult = searchIncidenceMatrixService.Search(searchText, normalization, steaming, lemmatization);
            } else if (indexType.equals("Lucene")) {
                searchResult = searchLuceneService.Search(searchText, normalization, steaming, lemmatization);
            } else if (indexType.equals("Inverted-index")) {
                searchResult = searchInvertedService.Search(searchText, normalization, steaming, lemmatization);
            } else if (indexType.equals("Positional-index")) {
                searchResult = searchPositionalIndexService.Search(searchText, normalization, steaming, lemmatization);
            } else if (indexType.equals("Bi-word-index")) {
                searchResult = searchBiWordIndexService.Search(searchText);
            }
        } catch (Exception exception) {
            log.error("Error in searching: {}", exception.getMessage());
        }

        return formatSearchResult(searchText, searchResult);
    }

    private String formatSearchResult(String searchText, List<Integer> searchResult) {
        if (searchResult.isEmpty()) {
            return "No documents found for query '" + searchText + "'.";
        }

        StringBuilder result = new StringBuilder();
        result.append("Found ").append(searchResult.size()).append(" document(s) that matched query '").append(searchText).append("':\n");
        for (Integer id : searchResult) {
            result.append("docID: ").append(id).append("\n");
        }
        return result.toString();
    }
}
