package com.information_retrieval.ir_project.service.search;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Override
    public String search(String indexType, String searchText, boolean normalization, boolean steaming, boolean lemetization) {
        log.info("Search parameters - Index Type: {}, Search Text: {}, Normalization: {}, Steaming: {}, Lemetization: {}",
                indexType, searchText, normalization, steaming, lemetization);

        return "Search result for '" + searchText + "' using " + indexType;
    }
}
