package com.example.enjoy_english.service;

import com.example.enjoy_english.model.QA;
import com.example.enjoy_english.tools.PageResult;
import com.example.enjoy_english.tools.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QAService {
    Page<QA> findAllByCategoryAndGroup(String category, String group, Pageable pageable);
    PageResult getQA(String category, String group, Pageable pageable);
    Result addQA(QA qa);
    Result updateQA(QA qa);
    Result deleteQAList(List<String> itemnoList);
}
