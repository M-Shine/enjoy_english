package com.example.enjoy_english.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "log")
public class Log {
    @Id
    @Column(name = "acc_no")
    private String accno;

    private String mac_address;

    @Column(name = "login_date_time")
    private Timestamp logindatetime;

    @Column(name = "logout_date_time")
    private Timestamp logoutdatetime;

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getLogindatetime() {
        return logindatetime;
    }

    public void setLogindatetime(Timestamp logindatetime) {
        this.logindatetime = logindatetime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getLogoutdatetime() {
        return logoutdatetime;
    }

    public void setLogoutdatetime(Timestamp logoutdatetime) {
        this.logoutdatetime = logoutdatetime;
    }
}
