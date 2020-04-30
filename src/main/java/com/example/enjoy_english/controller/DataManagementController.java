package com.example.enjoy_english.controller;

import com.example.enjoy_english.model.Menu;
import com.example.enjoy_english.model.QA;
import com.example.enjoy_english.service.MenuService;
import com.example.enjoy_english.service.QAService;
import com.example.enjoy_english.tools.PageResult;
import com.example.enjoy_english.tools.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
public class DataManagementController {
    @Resource
    private MenuService menuService;
    @Resource
    private QAService qaService;

    //查询全部菜单选项
    @GetMapping("/api/getMenus")
    public Result getMenus(){
        return new Result().success(null, menuService.findAll());
    }

    //按条件查询菜单选项
    @GetMapping("/management/searchMenu")
    public Result searchMenu(String groupno, String category, String group){
        return menuService.search(groupno, category, group);
    }

    //添加菜单选项
    @PostMapping("/management/addMenu")
    public Result addMenu(@Valid @RequestBody Menu menu, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()){
                return new Result().error(error.getDefaultMessage());
            }
        }
        return menuService.addMenu(menu);
    }

    //修改菜单项
    @PostMapping("/management/updateMenu")
    public Result updateMenu(@Valid @RequestBody Menu menu, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()){
                return new Result().error(error.getDefaultMessage());
            }
        }
        return menuService.updateMenu(menu);
    }

    //删除菜单选项
    @PostMapping("/management/deleteMenu")
    public Result deleteMenu(@RequestBody List<String> groupnoList){
        return menuService.deleteMenu(groupnoList);
    }

    //分页获取指定类别和组别下的QA资料
    @GetMapping("/api/getQA")
    public PageResult getQA(@NotBlank(message = "类别不能为空") String category,
                            @NotBlank(message = "组别不能为空") String group,
                            @PageableDefault(page = 0, size = 10) Pageable pageable){
        return qaService.getQA(category, group, pageable);
    }

    @GetMapping("/management/searchQA")
    public Result searchQA(String itemno, String category, String group, String keywordItemq, String keywordItema,
                        @PageableDefault(page = 0, size = 10) Pageable pageable){
        return qaService.searchQA(itemno, category, group, keywordItemq, keywordItema, pageable);
    }

    //增加QA资料
    @PostMapping("/management/addQA")
    public Result addQA(@Valid @RequestBody QA qa, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()){
                return new Result().error(error.getDefaultMessage());
            }
        }
        return qaService.addQA(qa);
    }

    //批量删除QA资料
    @PostMapping("/management/deleteQA")
    public Result deleteQAList(@RequestBody List<String> itemnoList){
        return qaService.deleteQAList(itemnoList);
    }

    //修改QA资料
    @PostMapping("/management/updateQA")
    public Result updateQA(@Valid @RequestBody QA qa, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()){
                return new Result().error(error.getDefaultMessage());
            }
        }
        return qaService.updateQA(qa);
    }

}
