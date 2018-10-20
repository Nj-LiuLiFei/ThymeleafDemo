package com.example.demo.pagehelper;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ExecutorUtil {

    private static Field additionalParametersField;

    static {
        try {
            additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
            additionalParametersField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("获取 BoundSql 属性 additionalParameters 失败: " + e, e);
        }
    }
    public static Map<String, Object> getAdditionalParameter(BoundSql boundSql) {
        try {
            return (Map<String, Object>) additionalParametersField.get(boundSql);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("获取 BoundSql 属性值 additionalParameters 失败: " + e, e);
        }
    }
    public static  Long getCount(Executor executor, MappedStatement ms, Object parameter ,ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        Map<String, Object> additionalParameters = getAdditionalParameter(boundSql);
        //创建 count 查询的缓存 key
        CacheKey countKey = executor.createCacheKey(ms, parameter, RowBounds.DEFAULT, boundSql);
        //调用方言获取 count sql
        String countSql = DialectSql.getCountSql(boundSql);
        BoundSql countBoundSql = new BoundSql(ms.getConfiguration(), countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        //当使用动态 SQL 时，可能会产生临时的参数，这些参数需要手动设置到新的 BoundSql 中
        for (String key : additionalParameters.keySet()) {
            countBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
        }
        Object countResultList = executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, countKey, countBoundSql);
        Long count = (Long) ((List) countResultList).get(0);
        return count;
    }
    public static <E> List<E> pageQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
                                        ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql,
                                        PageInfo page) throws SQLException {
        Map<String, Object> additionalParameters = getAdditionalParameter(boundSql);
        //生成分页的缓存 key
        //CacheKey pageKey = cacheKey;
        //调用方言获取 count sql
        String pageSql = DialectSql.getPageSql(boundSql,page.getPageOffset(),page.getPageSize());

        BoundSql pageBoundSql  = new BoundSql(ms.getConfiguration(), pageSql, boundSql.getParameterMappings(), boundSql);
        for (String key : additionalParameters.keySet()) {
            pageBoundSql .setAdditionalParameter(key, additionalParameters.get(key));
        }
        return executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, pageBoundSql );
    }
}
