package com.example.enjoy_english.repository;

import com.example.enjoy_english.model.QA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QARepository extends JpaRepository<QA, String> {
    Page<QA> findAllByCategoryAndGroup(String category, String group, Pageable pageable);
}
