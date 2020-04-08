package com.example.enjoy_english.service.impl;

import com.example.enjoy_english.model.QA;
import com.example.enjoy_english.repository.QARepository;
import com.example.enjoy_english.service.QAService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QAServiceImpl implements QAService {
    @Resource
    private QARepository qaRepository;

    @Override
    public List<QA> findAll() {
        return qaRepository.findAll();
    }
}
