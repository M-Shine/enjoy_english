package com.example.enjoy_english.repository;

import com.example.enjoy_english.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByAccno(String accno);
    void deleteByAccno(String accno);
    Page<User> findAll(Pageable pageable);
}
