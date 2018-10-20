package com.example.demo.pagehelper;

import java.util.ArrayList;
import java.util.List;

public class PageInfo<T> extends ArrayList<T> {

    /**
     * 分页偏移
     */
    private int pageOffset = 0;
    /**
     * 页面大小
     */
    private int pageSize = 10;
    /**
     * 页面大小数组
     * 防止前端非法获取全部数据 例如：0-1000000等。
     */
    private static int[] pageSizeArray = {10,15,20,30,50};
    /**
     * 排序方式
     * 升序、降序
     */
    private static String[]  sortStrArray ={"ASC","DESC"};
    /**
     * 排序列字段
     */
    private String orderBy ="";
    /**
     * 排序方式
     *
     */
    private String sortStr="";
    /**
     * 数据总数
     */
    private long total;
    /**
     * 数据结果集
     */
    private List<T> list;


    public PageInfo(int pageOffset, int pageSize){
        this(pageOffset,pageSize,null,null);
    }
    public PageInfo(int pageOffset, int pageSize, String orderBy, String sortStr){
        if(pageOffset>=0 && verifyPageSize(pageSize)){
            this.setPageOffset(pageOffset);
            this.setPageSize(pageSize);
        }
        if(verifySortStr(sortStr) && orderBy.isEmpty() == false){
            this.setOrderBy(orderBy);
            this.setSortStr(sortStr);
        }
    }

    private boolean verifyPageSize(int pageSize){
        for(int i=0;i<pageSizeArray.length;i++){
            if(pageSizeArray[i] == pageSize){
                return true;
            }
        }
        return false;
    }
    private boolean verifySortStr(String sortStr){
        for(int i=0;i<sortStrArray.length;i++){
            if(sortStrArray[i].equals(sortStr)){
                return true;
            }
        }
        return false;
    }
    public int getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(int pageOffset) {
        this.pageOffset = pageOffset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSortStr() {
        return sortStr;
    }

    public void setSortStr(String sortStr) {
        this.sortStr = sortStr;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
