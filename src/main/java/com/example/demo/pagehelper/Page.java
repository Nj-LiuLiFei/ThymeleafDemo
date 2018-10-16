package com.example.demo.pagehelper;

public class Page {

    /**
     * 分页偏移
     */
    private int pageOffset;

    /**
     * 页面大小
     */
    private int pageSize;

    /**
     * 页面大小数组
     * 防止前端非法获取全部数据 例如：0-1000000等。
     */
    private static int[] pageSizeArray = {10,15,20,30,50};

    /**
     * 是否进行count分页
     */
    private boolean count = false;

    /**
     * 参数是否合理，默认不合理返回空的结果集
     */
    private boolean reasonable = false;


    public Page(int pageOffset,int pageSize){
        this(pageOffset,pageSize,true);
    }
    public Page(int pageOffset,int pageSize,boolean count){
        if(pageOffset>=0 && verifyPageSize(pageSize)){
            this.setPageOffset(pageOffset);
            this.setPageSize(pageSize);
            this.setCount(count);
            this.setReasonable(true);
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

    public boolean isCount() {
        return count;
    }

    public void setCount(boolean count) {
        this.count = count;
    }

    public boolean isReasonable() {
        return reasonable;
    }

    public void setReasonable(boolean reasonable) {
        this.reasonable = reasonable;
    }
}
