package com.example.enjoy_english.service;

import com.example.enjoy_english.model.Menu;

import java.util.List;

public interface MenuService {
    // 查询全部菜单选项
    List<Menu> findAll();
    // 查询某一类别的菜单选项
    List<Menu> findByCategory(String category);
    // 查询全部的类
    List<String> findCategories();
    // 删除某一类及其所有分组
    int deleteByCategory(String category);
    // 删除某一分组
    int deleteByCategoryAndGroup(String category, String group);
    // 添加菜单
    int addMenu(Menu menu);
}
