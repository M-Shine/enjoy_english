package com.example.enjoy_english.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
//    @NotBlank(message = "用户名不能为空")
//    @Length(min = 1, max = 20, message = "用户名长度需在 1 至 20 个字符范围内")
    @Column(name = "acc_no")
    private String accno;

//    @Length(max = 64, message = "密码最大不能超过64个字符")
//    @JsonIgnore
    private String password;

    private String name;

    @Column(name = "tel_no")
    private String telno;

    // 角色默认值为1，不赋值的话该变量会被默认初始化为0
//    @JsonIgnore
//    @NotNull
    private int role = 1;

    @Column(name = "register_datetime")
//    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp registerdatetime;

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public Timestamp getRegisterdatetime() {
        return registerdatetime;
    }

    public void setRegisterdatetime(Timestamp registerdatetime) {
        this.registerdatetime = registerdatetime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "accno='" + accno + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", telno='" + telno + '\'' +
                ", role=" + role +
                ", registerdatetime=" + registerdatetime +
                '}';
    }
}
