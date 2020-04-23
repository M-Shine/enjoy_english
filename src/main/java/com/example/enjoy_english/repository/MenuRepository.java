package com.example.enjoy_english.repository;

import com.example.enjoy_english.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, String> {
    Menu findByCategoryAndGroup(String category, String group);

    @Transactional
    @Modifying
    @Query(value = "insert into menu(group_no, category, `group`) values (:#{#menu.groupno}, :#{#menu.category},:#{#menu.group})", nativeQuery = true)
    int addMenu(Menu menu);

    @Transactional
    @Modifying
    @Query(value = "delete from Menu where group_no in (?1)")
    int deleteByGroupnoList(List<String> groupnoList);

    @Transactional
    @Modifying
    @Query(value = "update menu set category=:#{#menu.category}, `group`=:#{#menu.group} where group_no=:#{#menu.groupno}", nativeQuery = true)
    int updateMenu(Menu menu);

    Menu findByGroupno(String groupno);

    @Query(value = "select * from menu where 1=1" +
            " and (?1 is null or group_no = ?1)" +
            " and (?2 is null or category like ?2)" +
            " and (?3 is null or `group` like ?3)", nativeQuery = true)
    List<Menu> search(String groupno, String category, String group);
}
