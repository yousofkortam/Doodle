package com.information_retrieval.ir_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorldController {

    @GetMapping("/")
    public String searcher() {
        return "search-page";
    }

    @GetMapping("/indexer")
    public String indexer() {
        return "indexer-page";
    }

}
