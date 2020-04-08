package com.example.enjoy_english.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "feedback")
public class Feedback {
    // 联合主键，包含editdatetime和accno
    @Id
    private FeedbackUnionKey feedbackUnionKey;
    private String content;
    private float reward;
    @Column(name = "show_out")
    // 默认展示，不赋值的话该变量会被默认初始化为false
    private boolean showout = true;

    public FeedbackUnionKey getFeedbackUnionKey() {
        return feedbackUnionKey;
    }

    public void setFeedbackUnionKey(FeedbackUnionKey feedbackUnionKey) {
        this.feedbackUnionKey = feedbackUnionKey;
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

    public boolean isShowout() {
        return showout;
    }

    public void setShowout(boolean showout) {
        this.showout = showout;
    }
}
