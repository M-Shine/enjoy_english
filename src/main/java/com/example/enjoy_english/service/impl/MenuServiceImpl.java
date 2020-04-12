package com.example.enjoy_english.service.impl;

import com.example.enjoy_english.model.Menu;
import com.example.enjoy_english.repository.MenuRepository;
import com.example.enjoy_english.service.MenuService;
import com.example.enjoy_english.tools.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuRepository menuRepository;

    @Override
    public HashMap<String, List<String>> findAll() {
        HashMap<String, List<String>> menus = new HashMap<>();
        List<Menu> menuList = menuRepository.findAll();
        for (Menu menu : menuList){
            menus.put(menu.getCategory(), new ArrayList<>());
        }
        for (Menu menu : menuList){
            menus.get(menu.getCategory()).add(menu.getGroup());
        }
        return menus;
    }

    @Override
    public Result addMenu(Menu menu) {
        if (menu.getCategory() == null || menu.getGroup() == null || menu.getCategory().trim().equals("")
                || menu.getGroup().trim().equals("") || menu.getCategory().length() > 20
                || menu.getGroup().length() > 20){
            return new Result().error("类别 / 组别 填写错误");
        }
        if (menuRepository.findByCategoryAndGroup(menu.getCategory(), menu.getGroup()) != null){
            return new Result().error("该菜单选项已存在");
        }
        menu.setGroupno(UUID.randomUUID().toString());
        menuRepository.addMenu(menu);
        return new Result().success("添加成功", menu);
    }

    @Override
    public Result deleteMenu(String groupno) {
        menuRepository.deleteByGroupno(groupno);
        return new Result().success("组代码为" + groupno + "的菜单项已删除", null);
    }

}
