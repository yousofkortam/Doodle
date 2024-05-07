package com.information_retrieval.ir_project.controller;

import com.information_retrieval.ir_project.service.indexing.IndexingService;
import com.information_retrieval.ir_project.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final IndexingService indexingService;

    @GetMapping("/")
    public String searcher() {
        return "search-page";
    }

    @GetMapping("/indexer")
    public String indexer() {
        return "indexer-page";
    }

    @PostMapping("/search")
    public String search(@RequestParam("indexType") String indexType,
                         @RequestParam("searchText") String searchText,
                         @RequestParam(value = "normalization", required = false) Boolean normalization,
                         @RequestParam(value = "steaming", required = false) Boolean steaming,
                         @RequestParam(value = "lemetization", required = false) Boolean lemetization,
                         Model model) {
        String result = searchService.search(indexType, searchText, normalization != null && normalization,
                steaming != null && steaming, lemetization != null && lemetization);
        model.addAttribute("indexType", indexType);
        model.addAttribute("searchText", searchText);
        model.addAttribute("result", result);
        return "search-page";
    }


    @PostMapping("/index")
    public String index(@RequestParam("indexType") String indexType,
                        @RequestParam(value = "normalization", required = false) Boolean normalization,
                        @RequestParam(value = "steaming", required = false) Boolean steaming,
                        @RequestParam(value = "lemetization", required = false) Boolean lemetization,
                        @RequestParam(value = "stopWords", required = false) Boolean stopWords,
                        @RequestParam(value = "tokenization", required = false) Boolean tokenization) {
        indexingService.index(indexType, normalization != null && normalization,
                steaming != null && steaming, lemetization != null && lemetization,
                stopWords != null && stopWords, tokenization != null && tokenization);
        return "redirect:/indexer";
    }

}
