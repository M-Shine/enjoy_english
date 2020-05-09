package com.example.enjoy_english;

import com.example.enjoy_english.model.Feedback;
import com.example.enjoy_english.model.FeedbackUnionKey;
import com.example.enjoy_english.repository.FeedbackRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EnjoyEnglishApplicationTests {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Test
    public void TestUnionKey(){
        String accno = "admin";
        String editdatetime = "2020-05-08 01:10:55";
        FeedbackUnionKey unionKey = new FeedbackUnionKey(Timestamp.valueOf(editdatetime), accno);
//        Feedback feedback = null;
        Optional<Feedback> optionalFeedback = feedbackRepository.findById(unionKey);
        Feedback feedback = optionalFeedback.orElse(null);
        if (feedback != null){
            System.out.println(feedback.toString());
        }else {
            System.out.println("It is null.");
        }
    }
}
