package com.example.enjoy_english.tools;

public enum UserRoles {
    //管理员
    ADMIN(0),
    //普通用户
    NORMAL(1);

    private int role;

    UserRoles(int role){
        this.role = role;
    }

    public int getRole(){
        return role;
    }
}
