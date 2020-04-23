package com.example.enjoy_english.service;

import com.example.enjoy_english.tools.Result;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface FeedbackService {
    Result addFeedback(Map<String, Object> data);
    Result updateFeedback(Map<String, Object> data);
    Result findAll(Pageable pageable, String accno, String startDatetime, String endDatetime, float minReward, float maxReward, String keyword);
}
