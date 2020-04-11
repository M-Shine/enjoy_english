package com.example.enjoy_english.service.impl;

import com.example.enjoy_english.model.QA;
import com.example.enjoy_english.repository.QARepository;
import com.example.enjoy_english.service.QAService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QAServiceImpl implements QAService {
    @Resource
    private QARepository qaRepository;

    @Override
    public Page<QA> findAllByCategoryAndGroup(String category, String group, Pageable pageable) {
        return qaRepository.findAllByCategoryAndGroup(category, group, pageable);
    }
}
