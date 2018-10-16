package com.example.demo.pagehelper;

public class PageHelper {
    private static final ThreadLocal<Page> LOCAL_PAGE = new ThreadLocal<Page>();

    /**
     * 设置 Page 参数
     *
     * @param page
     */
    protected static void setLocalPage(Page page) {
        LOCAL_PAGE.set(page);
    }

    /**
     * 获取 Page 参数
     *
     * @return
     */
    public static Page getLocalPage() {
        return LOCAL_PAGE.get();
    }

    /**
     * 移除本地变量
     */
    public static void clearPage() {
        LOCAL_PAGE.remove();
    }

    public static void startPage(int pageOffset,int pageSize){
        startPage(pageOffset,pageSize,true);
    }
    public static void startPage(int pageOffset,int pageSize,boolean count){
        Page page = new Page(pageOffset,pageSize);
        setLocalPage(page);
    }

}
