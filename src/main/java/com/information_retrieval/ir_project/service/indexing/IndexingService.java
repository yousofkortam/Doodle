package com.information_retrieval.ir_project.service.indexing;

public interface IndexingService {
    public void index(String indexType, boolean normalization, boolean steaming, boolean lemetization, boolean stopWords, boolean tokenization);
}
