package com.example.enjoy_english.controller;

import com.example.enjoy_english.service.MenuService;
import com.example.enjoy_english.tools.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserOperator {
    @Resource
    private MenuService menuService;

    @GetMapping("/api/menus")
    public Result getMenus(){
        return new Result(1, "menus", menuService.findAll());
    }

    @GetMapping("/api/getGroups")
    public Result getGroups(String category){
        return new Result(1, "groups", menuService.findByCategory(category));
    }

}
