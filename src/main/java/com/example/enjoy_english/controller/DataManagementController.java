package com.example.enjoy_english.controller;

import com.example.enjoy_english.model.Menu;
import com.example.enjoy_english.model.QA;
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

    //修改菜单项
    @PostMapping("/management/updateMenu")
    public Result updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }

    //删除菜单选项
    @GetMapping("/management/deleteMenu")
    public Result deleteMenu(@RequestParam String groupno){
        return menuService.deleteMenu(groupno);
    }

    //增加QA资料
    @PostMapping("/management/addQA")
    public Result addQA(@RequestBody QA qa){
        return qaService.addQA(qa);
    }

    //删除QA资料
    @GetMapping("/management/deleteQA")
    public Result deleteQA(@RequestParam String itemno){
        return qaService.deleteQA(itemno);
    }

    //修改QA资料
    @PostMapping("/management/updateQA")
    public Result updateQA(@RequestBody QA qa){
        return qaService.updateQA(qa);
    }

}
