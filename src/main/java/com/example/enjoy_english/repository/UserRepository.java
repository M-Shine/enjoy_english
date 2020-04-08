package com.example.enjoy_english.repository;

import com.example.enjoy_english.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByAccno(String accno);
    int deleteByAccno(String accno);
}
