package com.example.enjoy_english.tools;

public class Result {
    private int status;
    private String message;
    private Object data;

    public Result(){}

    public Result(int status, String message, Object data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static Result success(String message, Object data){
        return new Result(1, message, data);
    }

    public static Result error(String message){
        return new Result(0, message, null);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
