package com.example.enjoy_english.service.impl;

import com.example.enjoy_english.repository.MenuRepository;
import com.example.enjoy_english.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuRepository menuRepository;

    @Override
    public HashMap<String, List<String>> findAll() {
        HashMap<String, List<String>> menus = new HashMap<>();
        List<String> categories = menuRepository.findCategories();
        for (String category : categories){
            menus.put(category, menuRepository.findByCategory(category));
        }
        return menus;
    }

}
