package com.example.demo.pagehelper;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

public class DialectSql {

    private final static String DRIVER="MYSQL";

    public static String getCountSql( BoundSql boundSql){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SELECT COUNT(0) FROM ( ");
        stringBuffer.append(boundSql.getSql());
        stringBuffer.append(" ) MySql_Count");
        return stringBuffer.toString();
    }
    public static String getPageSql(BoundSql boundSql,int pageOffset,int pageSize){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(boundSql.getSql());
        stringBuffer.append(" LIMIT ");
        stringBuffer.append(pageOffset);
        stringBuffer.append(",");
        stringBuffer.append(pageSize);
        return stringBuffer.toString();
    }
}
