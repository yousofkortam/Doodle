package com.information_retrieval.ir_project.service.indexing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IndexingServiceImpl implements IndexingService{
    @Override
    public void index(String indexType, boolean normalization, boolean steaming, boolean lemetization, boolean stopWords, boolean tokenization) {
        log.info("indexType {}, normalization {}, steaming {}, lemetization {}, stopWords {}, tokenization {}",
                indexType, normalization,steaming, lemetization, stopWords, tokenization);
    }
}
