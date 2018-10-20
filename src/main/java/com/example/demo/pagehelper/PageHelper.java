package com.example.demo.pagehelper;

import java.util.List;

public class PageHelper {
    private static final ThreadLocal<PageInfo> LOCAL_PAGE = new ThreadLocal<PageInfo>();

    /**
     * 设置 Page 参数
     *
     * @param page
     */
    protected static void setLocalPage(PageInfo page) {
        LOCAL_PAGE.set(page);
    }

    /**
     * 获取 Page 参数
     *
     * @return
     */
    public static PageInfo getLocalPage() {
        return LOCAL_PAGE.get();
    }

    /**
     * 移除本地变量
     */
    public static void clearPage() {
        LOCAL_PAGE.remove();
        System.out.println("清除线程缓存："+getLocalPage());
    }

    public static void startPage(int pageOffset,int pageSize){
        startPage(pageOffset,pageSize,true);
    }
    public static void startPage(int pageOffset,int pageSize,boolean count){
        PageInfo page = new PageInfo(pageOffset,pageSize);
        setLocalPage(page);
    }
    public static PageInfo getPageInfo(long total, List list ){
        PageInfo pageInfo = getLocalPage();
        pageInfo.setList(list);
        pageInfo.setTotal(total);
        return pageInfo;
    }
}
