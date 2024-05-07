package com.information_retrieval.ir_project.service.search;

import java.util.List;

public interface SearchService {
    String search(String indexType, String searchText, boolean normalization, boolean steaming, boolean lemmatization);
}
