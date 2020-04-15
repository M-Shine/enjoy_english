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
    public HashMap findAll() {
        HashMap<String, List> menus = new HashMap<>();
        List<Menu> menuList = menuRepository.findAll();
        for (Menu menu : menuList){
            menus.put(menu.getCategory(), new ArrayList<>());
        }
        for (Menu menu : menuList){
            HashMap<String, String> map = new HashMap<>();
            map.put("groupno", menu.getGroupno());
            map.put("group", menu.getGroup());
            menus.get(menu.getCategory()).add(map);
        }
        return menus;
    }

    @Override
    public Result addMenu(Menu menu) {
        if (!isLegal(menu.getCategory()) || !isLegal(menu.getGroup())){
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
    public Result deleteMenu(List<String> groupnoList) {
        menuRepository.deleteByGroupnoList(groupnoList);
        return new Result().success("已删除", groupnoList);
    }

    @Override
    public Result updateMenu(Menu menu) {
        if (!isLegal(menu.getCategory()) || !isLegal(menu.getGroup())){
            return new Result().error("类别 / 组别 填写错误");
        }
        if (menuRepository.findByGroupno(menu.getGroupno()) == null){
            return new Result().error("组代码为" + menu.getGroupno() + "的菜单项不存在");
        }
        menuRepository.updateMenu(menu);
        return new Result().success("组代码为" + menu.getGroupno() + "的菜单项已修改", menu);
    }

    boolean isLegal(String str){
        if (str == null || str.trim().isEmpty() || str.length() > 20){
            return false;
        }
        return true;
    }

}
