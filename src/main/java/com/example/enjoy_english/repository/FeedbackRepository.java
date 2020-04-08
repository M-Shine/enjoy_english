package com.example.enjoy_english.repository;

import com.example.enjoy_english.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {
}
