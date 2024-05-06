package com.information_retrieval.ir_project.service;

import java.util.List;

public interface SearchService {
    List<Integer> Search(String SearchText, boolean Normalization, boolean Steaming, boolean Lemetization);
}
