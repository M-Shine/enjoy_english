package com.example.enjoy_english.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "menu")
public class Menu implements Serializable {
    @Id
    @Column(name = "group_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String groupno;

    @NotBlank(message = "类别不能为空")
    @Length(max = 20, message = "类别名不能超过20个字符")
    private String category;

    @NotBlank(message = "组别不能为空")
    @Length(max = 20, message = "组别名不能超过20个字符")
    private String group;

    public String getGroupno() {
        return groupno;
    }

    public void setGroupno(String groupno) {
        this.groupno = groupno;
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
}
