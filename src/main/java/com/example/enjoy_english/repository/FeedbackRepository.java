package com.example.enjoy_english.repository;

import com.example.enjoy_english.model.Feedback;
import com.example.enjoy_english.model.FeedbackUnionKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FeedbackRepository extends JpaRepository<Feedback, FeedbackUnionKey> {
    @Transactional
    @Modifying
    @Query(value = "update feedback set show_out=:#{#feedback.showout} " +
            "where acc_no=:#{#feedback.feedbackUnionKey.accno} and edit_date_time=:#{#feedback.feedbackUnionKey.editdatetime}",
            nativeQuery = true)
    int updateFeedback(Feedback feedback);

    @Query(value = "select * from feedback where 1=1" +
            " and (?1 is null or acc_no = ?1)" +
            " and (?2 is null or (edit_date_time >= ?2))" +
            " and (?3 is null or edit_date_time <= ?3)" +
            " and reward >= ?4" +
            " and (?5 < ?4 or reward <= ?5)" +
            " and (?6 is null or content like ?6)", nativeQuery = true)
    Page<Feedback> search(String accno, String startDatetime, String endDatetime, float minReward, float maxReward, String keyword, Pageable pageable);

}