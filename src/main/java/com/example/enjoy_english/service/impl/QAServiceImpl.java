package com.example.enjoy_english.service.impl;

import com.example.enjoy_english.model.QA;
import com.example.enjoy_english.repository.MenuRepository;
import com.example.enjoy_english.repository.QARepository;
import com.example.enjoy_english.service.QAService;
import com.example.enjoy_english.tools.PageResult;
import com.example.enjoy_english.tools.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class QAServiceImpl implements QAService {
    @Resource
    private QARepository qaRepository;
    @Resource
    private MenuRepository menuRepository;

    @Override
    public Page<QA> findAllByCategoryAndGroup(String category, String group, Pageable pageable) {
        return qaRepository.findAllByCategoryAndGroup(category, group, pageable);
    }

    @Override
    public PageResult getQA(String category, String group, Pageable pageable) {
        if (isEmpty(category) || isEmpty(group)){
            return new PageResult().error("类别 / 组别 不能为空");
        }
        Page<QA> qaPage = findAllByCategoryAndGroup(category, group, pageable);
        List<QA> qaList = qaPage.getContent();
        List<HashMap<String, String>> result = new ArrayList<>();
        for (QA qa : qaList){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("itemno", qa.getItemno());
            map.put("itemq", qa.getItemq());
            map.put("itema", qa.getItema());
            result.add(map);
        }
        return new PageResult().success(null, result, qaPage.getTotalPages(), qaPage.getNumber(), qaPage.getSize());
    }

    @Override
    public Result addQA(QA qa) {
        Result result = isLegal(qa);
        if (result.getStatus() == 0){
            return result;
        }
        qa.setItemno(UUID.randomUUID().toString());
        qaRepository.addQA(qa);
        return new Result().success("添加QA资料成功", qa);
    }

    @Transactional
    @Override
    public Result updateQA(QA qa){
        Result result = isLegal(qa);
        if (result.getStatus() == 0){
            return result;
        }
        qaRepository.updateQA(qa);
        return new Result().success("修改QA资料成功", qa);
    }

    @Override
    public Result deleteQAList(List<String> itemnoList) {
        qaRepository.deleteQA(itemnoList);
        return new Result().success("已删除", itemnoList);
    }

    boolean isEmpty(String str){
        if (str == null || str.trim().isEmpty()){
            return true;
        }
        return false;
    }

    Result isLegal(QA qa){
        if (menuRepository.findByCategoryAndGroup(qa.getCategory(), qa.getGroup()) == null){
            return new Result().error("类别 & 组别 不存在");
        }
        if (isEmpty(qa.getItemq()) || isEmpty(qa.getItema())){
            return new Result().error("问题 / 答案 不能为空");
        }
        if (qa.getItemq().length() > 30 || qa.getItema().length() > 30){
            return new Result().error("问题 / 答案 长度超过限制（30个字符）");
        }
        return new Result().success(null, qa);
    }

}
