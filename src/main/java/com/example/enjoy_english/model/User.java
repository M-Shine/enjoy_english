package com.example.enjoy_english.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @Column(name = "acc_no")
    private String accno;
    private String password;
    private String name;
    @Column(name = "tel_no")
    private String telno;
    // 角色默认值为1，不赋值的话该变量会被默认初始化为0
    private short role = 1;
    @Column(name = "register_datetime")
    private Timestamp registerdatetime;

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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

    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
    }
}
