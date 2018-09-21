package com.example.demo.mybatis.interceptor;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;

@Component
//拦截StatementHandler中参数类型为Connection的prepare方法
@Intercepts({@Signature(type= StatementHandler.class,method="prepare",args={Connection.class,Integer.class})})
public class MyPageInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("intercept执行了");
        StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
        //通过MetaObject优雅访问对象的属性，这里是访问statementHandler的属性
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        //先拦截到RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，其实现类是BaseStatementHandler，然后就到BaseStatementHandler的成员变量mappedStatement
        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
        // 配置文件中SQL语句的ID
        String id = mappedStatement.getId();
        if(id.matches(".+ByPage$")) { //需要拦截的ID(正则匹配)
            BoundSql boundSql = statementHandler.getBoundSql();
            // 原始的SQL语句
            String sql = boundSql.getSql();
            // 查询总条数的SQL语句
            String countSql = "select count(0) from (" + sql + ")";
            //执行总条数SQL语句的查询
            Connection connection = (Connection)invocation.getArgs()[0];
            PreparedStatement countStatement = connection.prepareStatement(countSql);
            ////获取参数信息即where语句的条件信息，注意上面拿到的sql中参数还是用?代替的
            ParameterHandler parameterHandler = (ParameterHandler)metaObject.getValue("delegate.parameterHandler");
            parameterHandler.setParameters(countStatement);
            ResultSet rs = countStatement.executeQuery();

            Map<?,?> parameter = (Map<?,?>)boundSql.getParameterObject();
           /* Page page = (Page)parameter.get("page");
            if(rs.next()) {
                page.setTotalNumber(rs.getInt(1));
            }*/
            // 改造后带分页查询的SQL语句
           /* String pageSql = sql + " limit " + page.getDbIndex() + "," + page.getDbNumber();*/
            metaObject.setValue("delegate.boundSql.sql", sql);
        }
        return invocation.proceed();
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
}
