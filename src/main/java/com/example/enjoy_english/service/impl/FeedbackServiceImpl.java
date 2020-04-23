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

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Resource
    private FeedbackRepository feedbackRepository;

    @Override
    public Result addFeedback(Map<String, Object> data) {
        if (data.get("accno") == null){
            return new Result().error("账号不存在");
        }

        float reward = 0;
        Object rewardObject = data.get("reward");
        Pattern pattern = Pattern.compile("\\d{1,6}[.]?\\d*");
        if (rewardObject != null && pattern.matcher(data.get("reward").toString()).matches()){
            reward = Float.parseFloat(rewardObject.toString());
            //保留两位小数，四舍五入
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberFormat.setMaximumFractionDigits(2);
            reward = Float.parseFloat(numberFormat.format(reward));
        }
        if (reward < 0 || reward > 9999.99){
            return new Result().error("打赏金额超出限制（最小0，最大9999.99）");
        }

        String content = String.valueOf(data.get("content"));
        if ((content == null || content.trim().equals("") || content.equals("null")) && reward == 0 ){
            return new Result().error("无效反馈");
        }

        FeedbackUnionKey feedbackUnionKey = new FeedbackUnionKey(new Timestamp(new Date().getTime()), data.get("accno").toString());
        Feedback feedback = new Feedback(feedbackUnionKey, content, reward, true);
        feedbackRepository.save(feedback);
        return new Result().success("反馈成功", null);
    }

    @Override
    public Result updateFeedback(Map<String, Object> data) {
        if (data.get("accno") == null || data.get("editdatetime") == null){
            return new Result().error("不存在该反馈记录");
        }
        String accno;
        Timestamp timestamp;
        boolean showout;
        try{
            accno = data.get("accno").toString();
            timestamp = Timestamp.valueOf(data.get("editdatetime").toString());
            showout = (boolean)data.get("showout");
        } catch (Exception e){
            e.printStackTrace();
            return new Result().error("参数传递错误");
        }
        FeedbackUnionKey feedbackUnionKey = new FeedbackUnionKey(timestamp, accno);
        Feedback feedback = new Feedback(feedbackUnionKey, null, 0, showout);
        feedbackRepository.updateFeedback(feedback);
        return new Result().success("修改成功", null);
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
        return new PageResult().success(null, formatValue(feedbackPage.getContent()),
                feedbackPage.getTotalPages(), feedbackPage.getNumber(), feedbackPage.getSize());
    }

    boolean isEmpty(String str){
        if (str == null || str.trim().isEmpty()){
            return true;
        }
        return false;
    }

    List formatValue(List<Feedback> feedbackList){
        List<HashMap> result = new ArrayList<>();
        for (Feedback feedback : feedbackList){
            HashMap<String, Object> map = new HashMap<>();
            map.put("accno", feedback.getFeedbackUnionKey().getAccno());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            map.put("editdatetime", dateFormat.format(feedback.getEditdatetime()));
            map.put("content", feedback.getContent());
            map.put("reward", feedback.getReward());
            map.put("showout", feedback.getShowout());
            result.add(map);
        }
        return result;
    }
}
