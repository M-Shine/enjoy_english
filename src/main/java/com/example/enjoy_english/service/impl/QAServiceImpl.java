package com.example.enjoy_english.service.impl;

import com.example.enjoy_english.model.QA;
import com.example.enjoy_english.repository.QARepository;
import com.example.enjoy_english.service.QAService;
import com.example.enjoy_english.tools.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class QAServiceImpl implements QAService {
    @Resource
    private QARepository qaRepository;

    @Override
    public Page<QA> findAllByCategoryAndGroup(String category, String group, Pageable pageable) {
        return qaRepository.findAllByCategoryAndGroup(category, group, pageable);
    }

    @Override
    public PageResult getQA(String category, String group, Pageable pageable) {
        if (category == null || group == null){
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
}
