package com.example.enjoy_english.service;

import com.example.enjoy_english.model.Feedback;
import com.example.enjoy_english.tools.Result;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {
    Result addFeedback(Feedback feedback);
    Result updateFeedback(Feedback feedback);
    Result findAll(Pageable pageable, String accno, String startDatetime, String endDatetime, float minReward, float maxReward, String keyword);
}
