package com.example.enjoy_english.service.impl;

import com.example.enjoy_english.model.Log;
import com.example.enjoy_english.repository.LogRepository;
import com.example.enjoy_english.service.LogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LogServiceImpl implements LogService {
    @Resource
    private LogRepository logRepository;

    @Override
    public Log add(Log log) {
        return logRepository.save(log);
    }
}
