package com.example.enjoy_english.repository;

import com.example.enjoy_english.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MenuRepository extends JpaRepository<Menu, String> {
    Menu findByCategoryAndGroup(String category, String group);

    @Transactional
    @Modifying
    @Query(value = "insert into menu(group_no, category, `group`) values (:#{#menu.groupno}, :#{#menu.category},:#{#menu.group})", nativeQuery = true)
    int addMenu(Menu menu);

    @Transactional
    @Modifying
    @Query(value = "delete from Menu where group_no=?1")
    int deleteByGroupno(String groupno);
}
