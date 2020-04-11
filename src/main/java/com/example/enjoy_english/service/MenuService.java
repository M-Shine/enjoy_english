package com.example.enjoy_english.service;

import java.util.HashMap;
import java.util.List;

public interface MenuService {
    // 查询全部菜单选项
    HashMap<String, List<String>> findAll();
}
