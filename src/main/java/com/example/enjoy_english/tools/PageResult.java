package com.example.enjoy_english.tools;

public class PageResult {
    private int status;
    private String message;
    private Object data;
    private int page;
    private int size;

    public PageResult(){}

    public PageResult(int status, String message, Object data, int page, int size){
        this.status = status;
        this.message = message;
        this.data = data;
        this.page = page;
        this.size = size;
    }

    public PageResult success(String message, Object data, int pageSize, int itemSize){
        return new PageResult(1, message, data, pageSize, itemSize);
    }

    public PageResult error(String message){
        return new PageResult(0, message, null, 0, 0);
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
