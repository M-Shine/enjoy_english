package com.example.enjoy_english.repository;

import com.example.enjoy_english.model.QA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QARepository extends JpaRepository<QA, String> {
    Page<QA> findAllByCategoryAndGroup(String category, String group, Pageable pageable);

    QA findByItemno(String itemno);

    @Transactional
    @Modifying
    @Query(value = "insert into qa(item_no, category, `group`, item_q, item_a) " +
            "values (:#{#qa.itemno}, :#{#qa.category},:#{#qa.group}, :#{#qa.itemq}, :#{#qa.itema})"
            , nativeQuery = true)
    int addQA(QA qa);

    @Transactional
    @Modifying
    @Query(value = "update qa set category=:#{#qa.category}, `group`=:#{#qa.group}," +
            "item_q=:#{#qa.itemq}, item_a=:#{#qa.itema} where item_no=:#{#qa.itemno}"
            , nativeQuery = true)
    int updateQA(QA qa);

    @Transactional
    @Modifying
    @Query(value = "delete from QA where item_no in (?1)")
    int deleteQA(List<String> itemnoList);
}
