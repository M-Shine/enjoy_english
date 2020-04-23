package com.example.enjoy_english.service;

import com.example.enjoy_english.model.Menu;
import com.example.enjoy_english.tools.Result;

import java.util.HashMap;
import java.util.List;

public interface MenuService {
    // 查询全部菜单选项
    HashMap findAll();
    //添加菜单选项
    Result addMenu(Menu menu);
    //删除菜单选项
    Result deleteMenu(List<String> groupnoList);
    //修改菜单选项
    Result updateMenu(Menu menu);
    //根据条件查询菜单选项
    Result search(String groupno, String category, String group);
}
