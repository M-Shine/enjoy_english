package com.example.enjoy_english.controller;

import com.example.enjoy_english.model.QA;
import com.example.enjoy_english.service.MenuService;
import com.example.enjoy_english.service.QAService;
import com.example.enjoy_english.tools.PageResult;
import com.example.enjoy_english.tools.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        if (category == null || group == null){
            return new PageResult().error("类别 / 组别 不能为空");
        }
        Page<QA> qaPage = qaService.findAllByCategoryAndGroup(category, group, pageable);
        List<QA> qaList = qaPage.getContent();
        List<HashMap<String, String>> result = new ArrayList<>();
        for (QA qa : qaList){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("itemq", qa.getItemq());
            map.put("itema", qa.getItema());
            result.add(map);
        }
        return new PageResult().success(null, result, qaPage.getNumber(), qaPage.getSize());
    }

}
