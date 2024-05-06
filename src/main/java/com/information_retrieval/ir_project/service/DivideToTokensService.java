package com.information_retrieval.ir_project.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DivideToTokensService {
    
    public List<String> Tokens(String SearchText) {
        List<String> ret = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < SearchText.length(); i++) {
            if (SearchText.charAt(i) == '*' || SearchText.charAt(i) == '&' || SearchText.charAt(i) == '|' || SearchText.charAt(i) == ' ') {
                if (!current.isEmpty())
                    ret.add(current.toString());

                current = new StringBuilder();
                continue;
            }
            current.append(SearchText.charAt(i));
        }

        if (!current.isEmpty())
            ret.add(current.toString());

        return ret;
    }
    
}
