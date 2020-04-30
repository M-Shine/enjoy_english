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
    public PageResult getQA(String category, String group, Pageable pageable) {
        Page<QA> qaPage = qaRepository.findAllByCategoryAndGroup(category, group, pageable);
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
    public Result searchQA(String itemno, String category, String group, String keywordItemq, String keywordItema, Pageable pageable) {
        if (isEmpty(itemno)){
            itemno = null;
        }
        if (isEmpty(category)){
            category = null;
        }
        if (isEmpty(group)){
            group = null;
        }
        if (isEmpty(keywordItemq)){
            keywordItemq = null;
        }else {
            keywordItemq = "%" + keywordItemq + "%";
        }
        if (isEmpty(keywordItema)){
            keywordItema = null;
        }else {
            keywordItema = "%" + keywordItema + "%";
        }
        Page<QA> qaPage = qaRepository.search(itemno, category, group, keywordItemq, keywordItema, pageable);
        return new PageResult().success(null, qaPage.getContent(), qaPage.getTotalPages(), qaPage.getNumber(), qaPage.getSize());
    }

    @Override
    public Result addQA(QA qa) {
        if (menuRepository.findByCategoryAndGroup(qa.getCategory(), qa.getGroup()) == null){
            return new Result().error("类别 & 组别 不存在");
        }
        qa.setItemno(UUID.randomUUID().toString());
        qaRepository.addQA(qa);
        return new Result().success("添加QA资料成功", qa);
    }

    @Transactional
    @Override
    public Result updateQA(QA qa){
        QA oldQA = qaRepository.findByItemno(qa.getItemno());
        if (oldQA == null){
            return new Result().error("指标号为 " + qa.getItemno() + " 的QA资料不存在");
        }
        if (!oldQA.getCategory().equals(qa.getCategory()) || !oldQA.getGroup().equals(qa.getGroup())){
            if (menuRepository.findByCategoryAndGroup(qa.getCategory(), qa.getGroup()) == null){
                return new Result().error("类别 & 组别 不存在");
            }
        }
        int statu = qaRepository.updateQA(qa);
        if(statu <= 0){
            return new Result().error("修改失败");
        }
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

}
