package com.example.enjoy_english.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "feedback")
public class Feedback {
    // 联合主键，包含editdatetime和accno
    @Id
    private FeedbackUnionKey feedbackUnionKey;

    private String content;

    private float reward = 0;

    @Column(name = "show_out")
    // 默认展示，不赋值的话该变量会被默认初始化为false
    private boolean showout = true;

    public Feedback(){}

    public Feedback(FeedbackUnionKey feedbackUnionKey, String content, float reward, boolean showout) {
        this.feedbackUnionKey = feedbackUnionKey;
        this.content = content;
        this.reward = reward;
        this.showout = showout;
    }

    public FeedbackUnionKey getFeedbackUnionKey() {
        return feedbackUnionKey;
    }

    public void setFeedbackUnionKey(FeedbackUnionKey feedbackUnionKey) {
        this.feedbackUnionKey = feedbackUnionKey;
    }

    public Timestamp getEditdatetime() {
        return feedbackUnionKey.getEditdatetime();
    }

    public void setEditdatetime(Timestamp editdatetime) {
        feedbackUnionKey.setEditdatetime(editdatetime);
    }

    public String getAccno() {
        return feedbackUnionKey.getAccno();
    }

    public void setAccno(String accno) {
        feedbackUnionKey.setAccno(accno);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getReward() {
        return reward;
    }

    public void setReward(float reward) {
        this.reward = reward;
    }

    public boolean getShowout() {
        return showout;
    }

    public void setShowout(boolean showout) {
        this.showout = showout;
    }
}
