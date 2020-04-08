package com.example.enjoy_english.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "qa")
public class QA implements Serializable {
    @Id
    @Column(name = "item_no")
    private String itemno;
    private String category;
    private String group;
    @Column(name = "item_q")
    private String itemq;
    @Column(name = "item_a")
    private String itema;

    public String getItemno() {
        return itemno;
    }

    public void setItemno(String itemno) {
        this.itemno = itemno;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getItemq() {
        return itemq;
    }

    public void setItemq(String itemq) {
        this.itemq = itemq;
    }

    public String getItema() {
        return itema;
    }

    public void setItema(String itema) {
        this.itema = itema;
    }
}
