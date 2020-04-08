package com.example.enjoy_english.repository;

import com.example.enjoy_english.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, String> {
    // 由于带有MySQL保留字，需指定“nativeQuery = true”使用原生sql语句才可以使用“``”进行转义
    // 注意sql语句中的“Menu”对应的是实体类“com\example\enjoy_english\model\Menu.java”而非数据库中的表名
//    @Query(value = "select distinct `group` from Menu where category = ?1", nativeQuery = true)
//    List<String> findByCategory(String category);
    @Query(value = "select distinct category from Menu")
    List<String> findCategories();
    int deleteByCategory(String category);
    @Transactional
    @Modifying
    @Query(value = "delete from Menu where category=?1 and `group`=?2", nativeQuery = true)
    int deleteByCategoryAndGroup(String category, String group);
    @Transactional
    @Modifying
    @Query(value = "insert into menu(category,`group`) values (:#{#menu.category},:#{#menu.group})", nativeQuery = true)
    int addMenu(Menu menu);
    List<Menu> findByCategory(String category);
}
