package com.example.enjoy_english.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class FeedbackUnionKey implements Serializable {
    @Column(name = "edit_date_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp editdatetime;

    @Column(name = "acc_no")
    private String accno;

    public FeedbackUnionKey() { }

    public FeedbackUnionKey(Timestamp editdatetime, String accno) {
        this.editdatetime = editdatetime;
        this.accno = accno;
    }

    public Timestamp getEditdatetime() {
        return editdatetime;
    }

    public void setEditdatetime(Timestamp editdatetime) {
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

    @Override
    public String toString() {
        return "editdatetime=" + editdatetime +
                ", accno='" + accno + '\'';
    }
}
