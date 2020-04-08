package com.example.enjoy_english.service.impl;

import com.example.enjoy_english.model.Menu;
import com.example.enjoy_english.repository.MenuRepository;
import com.example.enjoy_english.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuRepository menuRepository;

    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Override
    public List<Menu> findByCategory(String category) {
        return menuRepository.findByCategory(category);
    }

    @Override
    public List<String> findCategories() {
        return menuRepository.findCategories();
    }

    @Transactional
    @Override
    public int deleteByCategory(String category) {
        return menuRepository.deleteByCategory(category);
    }

    @Transactional
    @Override
    public int deleteByCategoryAndGroup(String category, String group) {
        return menuRepository.deleteByCategoryAndGroup(category, group);
    }

    @Override
    public int addMenu(Menu menu) {
        return menuRepository.addMenu(menu);
    }
}
