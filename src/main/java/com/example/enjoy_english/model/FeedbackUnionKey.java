package com.example.enjoy_english.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FeedbackUnionKey implements Serializable {
    @Column(name = "edit_date_time")
    private String editdatetime;
    @Column(name = "acc_no")
    private String accno;

    public FeedbackUnionKey() { }

    public FeedbackUnionKey(String editdatetime, String accno) {
        this.editdatetime = editdatetime;
        this.accno = accno;
    }

    public String getEditdatetime() {
        return editdatetime;
    }

    public void setEditdatetime(String editdatetime) {
        this.editdatetime = editdatetime;
    }

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackUnionKey that = (FeedbackUnionKey) o;
        return editdatetime.equals(that.editdatetime) &&
                accno.equals(that.accno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(editdatetime, accno);
    }
}
