package com.example.enjoy_english.repository;

import com.example.enjoy_english.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByAccno(String accno);

    Page<User> findAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "delete from User where acc_no in (?1)")
    int deleteByAccno(List<String> accnoList);

}
