package com.example.enjoy_english.controller;

import com.example.enjoy_english.model.Menu;
import com.example.enjoy_english.service.MenuService;
import com.example.enjoy_english.service.QAService;
import com.example.enjoy_english.tools.PageResult;
import com.example.enjoy_english.tools.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class DataManagementController {
    @Resource
    private MenuService menuService;
    @Resource
    private QAService qaService;

    //获取全部菜单选项
    @GetMapping("/api/getMenus")
    public Result getMenus(){
        return new Result().success(null, menuService.findAll());
    }

    //分页获取指定类别和组别下的QA资料
    @GetMapping("/api/getQA")
    public PageResult getQA(String category, String group,
                        @PageableDefault(page = 0, size = 10)Pageable pageable){
        return qaService.getQA(category, group, pageable);
    }

    //添加菜单选项
    @PostMapping("/management/addMenu")
    public Result addMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }

    //删除菜单选项
    @GetMapping("/management/deleteMenu")
    public Result deleteMenu(@RequestParam String groupno){
        return menuService.deleteMenu(groupno);
    }

}
