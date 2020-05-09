package com.example.enjoy_english.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "qa")
public class QA implements Serializable {
    @Id
    @Column(name = "item_no")
    private String itemno;

    @NotBlank(message = "类别不能为空")
    @Length(max = 20, message = "类别名不能超过20个字符")
    private String category;

    @NotBlank(message = "组别不能为空")
    @Length(max = 20, message = "组别名不能超过20个字符")
    private String group;

    @Column(name = "item_q")
    @NotBlank(message = "问题不能为空")
    @Length(max = 30, message = "问题不能超过30个字符")
    private String itemq;

    @Column(name = "item_a")
    @NotBlank(message = "答案不能为空")
    @Length(max = 30, message = "答案不能超过30个字符")
    private String itema;

    @Override
    public String toString() {
        return "QA{" +
                "itemno='" + itemno + '\'' +
                ", category='" + category + '\'' +
                ", group='" + group + '\'' +
                ", itemq='" + itemq + '\'' +
                ", itema='" + itema + '\'' +
                '}';
    }

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
