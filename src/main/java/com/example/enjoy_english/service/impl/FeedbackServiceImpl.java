package com.example.enjoy_english.service.impl;

import com.example.enjoy_english.model.Feedback;
import com.example.enjoy_english.model.FeedbackUnionKey;
import com.example.enjoy_english.repository.FeedbackRepository;
import com.example.enjoy_english.service.FeedbackService;
import com.example.enjoy_english.tools.PageResult;
import com.example.enjoy_english.tools.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Resource
    private FeedbackRepository feedbackRepository;

    @Override
    @Transactional
    public Result addFeedback(Feedback feedback){
        if (feedback.getReward() < 0 || feedback.getReward() > 9999.99){
            return new Result().error("打赏金额超出限制（最小0，最大9999.99）");
        }
        if ( (feedback.getContent() == null || "".equals(feedback.getContent()) || "null".equals(feedback.getContent())) && feedback.getReward() == 0){
            return new Result().error("无效反馈");
        }
        FeedbackUnionKey feedbackUnionKey = feedback.getFeedbackUnionKey();
        feedbackUnionKey.setEditdatetime(new Timestamp(new Date().getTime()));
        feedback.setFeedbackUnionKey(feedbackUnionKey);
        feedbackRepository.save(feedback);
        Map<String, String> map = formatValue(feedback);
        map.remove("showout");
        return new Result().success("反馈成功", map);
    }

    @Override
    @Transactional
    public Result updateFeedback(Feedback feedback){
        Feedback oldFeedback = feedbackRepository.findById( feedback.getFeedbackUnionKey() ).orElse(null);
        if (oldFeedback == null){
            return new Result().error("不存在该反馈记录，请检查用户名与反馈时间是否正确");
        }
        oldFeedback.setShowout( feedback.getShowout() );
        int statu = feedbackRepository.updateFeedback( oldFeedback );
        if ( statu <= 0 ){
            return new Result().error("修改反馈记录失败");
        }
        return new Result().success("修改反馈记录成功", oldFeedback);
    }

    @Override
    public Result findAll(Pageable pageable, String accno, String startDatetime, String endDatetime, float minReward, float maxReward, String keyword) {
        if (isEmpty(accno)){
            accno = null;
        }
        if (minReward < 0){
            minReward = 0;
        }
        if (maxReward == 0){
            maxReward = -1;
        }
        //匹配格式为“yyyy-MM-dd HH:mm:ss”的日期时间
        Pattern isDatetime = Pattern.compile("(19|20)\\d{2}-((0[1-9])|(1[0-2]))-((0[1-9])|([1-2]\\d)|(3[0-1])) (([0-1]\\d)|(2[0-3])):[0-5]\\d:[0-5]\\d");
        if (isEmpty(startDatetime) || !isDatetime.matcher(startDatetime).matches()){
            startDatetime = null;
        }
        if (isEmpty(endDatetime) || isDatetime.matcher(endDatetime).matches()){
            endDatetime = null;
        }
        if (startDatetime != null && endDatetime != null && Timestamp.valueOf(endDatetime).before( Timestamp.valueOf(startDatetime) )){
            endDatetime = null;
        }
        if (isEmpty(keyword)){
            keyword = null;
        }else {
            keyword = "%" + keyword + "%";
        }
        Page<Feedback> feedbackPage = feedbackRepository.search(accno, startDatetime, endDatetime, minReward, maxReward, keyword, pageable);
        return new PageResult().success(null, getFeedbackList(feedbackPage.getContent()),
                feedbackPage.getTotalPages(), feedbackPage.getNumber(), feedbackPage.getSize());
    }

    boolean isEmpty(String str){
        if (str == null || str.trim().isEmpty()){
            return true;
        }
        return false;
    }

    Map formatValue(Feedback feedback){
        Map<String, Object> map = new HashMap<>();
        map.put("accno", feedback.getFeedbackUnionKey().getAccno());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("editdatetime", dateFormat.format(feedback.getFeedbackUnionKey().getEditdatetime()));
        map.put("content", feedback.getContent());
        map.put("reward", feedback.getReward());
        map.put("showout", feedback.getShowout());
        return map;
    }

    List getFeedbackList(List<Feedback> feedbackList){
        List<Map> result = new ArrayList<>();
        for (Feedback feedback : feedbackList){
            result.add( formatValue(feedback) );
        }
        return result;
    }
}
