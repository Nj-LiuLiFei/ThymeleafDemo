package com.example.demo.pagehelper;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;

import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import sun.security.util.Cache;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Component
//拦截Executor中参数类型为Connection的query方法
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
public class PageHelperInterceptor implements Interceptor {

    protected Cache<String, MappedStatement> msCountMap = null;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("intercept执行了");

        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        String msId = ms.getId();
        if(!msId.matches(".+ByQuery$")) { //需要拦截的ID(正则匹配)
            return invocation.proceed();
        }

        try {
            Object parameter = args[1];
            RowBounds rowBounds = (RowBounds) args[2];
            ResultHandler resultHandler = (ResultHandler) args[3];
            Executor executor = (Executor) invocation.getTarget();
            CacheKey cacheKey;
            BoundSql boundSql;
            //由于逻辑关系，只会进入一次
            if (args.length == 4) {
                //4 个参数时
                boundSql = ms.getBoundSql(parameter);
                cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
            } else {
                //6 个参数时
                cacheKey = (CacheKey) args[4];
                boundSql = (BoundSql) args[5];
            }
            //checkDialectExists();
            List resultList;
            long total=0;
            MappedStatement countMs= MSUtil.newCountMappedStatement(ms,msId+"_COUNT");
            total= ExecutorUtil.getCount(executor,countMs,parameter,resultHandler,boundSql);
            if(total == 0){
                return PageHelper.getPageInfo(total,new ArrayList());
            }
            resultList = ExecutorUtil.pageQuery(executor,ms, parameter, rowBounds, resultHandler, cacheKey, boundSql,PageHelper.getLocalPage());
            return PageHelper.getPageInfo(total,resultList);
        }catch (SQLException e){
            return new ArrayList<>();
        }finally {
            PageHelper.clearPage();
        }
    }

    @Override
    public Object plugin(Object target) {
        System.out.println("Plugin执行了");
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("setProperties执行了");
    }

    public String setQueryTotalSql(String sql){
        return "SELECT COUNT(*) " +sql.substring(sql.indexOf("FROM"));
    }
}
