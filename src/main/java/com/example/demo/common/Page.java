package com.example.demo.common;

import java.util.ArrayList;
import java.util.List;

public class Page<E> extends ArrayList<E> {

    private int pageStart = 1;// 页码，默认是第一页
    private int pageDisplayLength = 10;// 每页显示的记录数，默认是10
    private int totalRecord;// 总记录数
    private int totalPage;// 总页数

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getPageDisplayLength() {
        return pageDisplayLength;
    }

    public void setPageDisplayLength(int pageDisplayLength) {
        this.pageDisplayLength = pageDisplayLength;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
