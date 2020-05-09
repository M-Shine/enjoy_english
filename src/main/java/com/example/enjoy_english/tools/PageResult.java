package com.example.enjoy_english.tools;

public class PageResult extends Result{
    private int pagenum;
    private int page;
    private int size;

    public PageResult(){}

    @Override
    public String toString() {
        return "PageResult{" +
                super.toString() +
                "pagenum=" + pagenum +
                ", page=" + page +
                ", size=" + size +
                '}';
    }

    public PageResult(int status, String message, Object data, int pagenum, int page, int size){
        super(status, message, data);
        this.pagenum = pagenum;
        this.page = page;
        this.size = size;
    }

    public PageResult success(String message, Object data, int pagenum, int pageSize, int itemSize){
        return new PageResult(1, message, data, pagenum, pageSize, itemSize);
    }

    public PageResult error(String message){
        return new PageResult(0, message, null, 0, 0, 0);
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

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }
}
