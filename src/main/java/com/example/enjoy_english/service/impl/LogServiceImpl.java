package com.example.enjoy_english.service.impl;

import com.example.enjoy_english.model.Log;
import com.example.enjoy_english.repository.LogRepository;
import com.example.enjoy_english.service.LogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class LogServiceImpl implements LogService {
    @Resource
    private LogRepository logRepository;

    @Override
    @Transactional
    public Log add(Log log) {
        return logRepository.save(log);
    }
}
