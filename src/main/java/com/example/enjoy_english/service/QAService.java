package com.example.enjoy_english.service;

import com.example.enjoy_english.model.QA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QAService {
    Page<QA> findAllByCategoryAndGroup(String category, String group, Pageable pageable);
}
