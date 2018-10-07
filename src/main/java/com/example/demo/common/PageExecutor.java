package com.example.demo.common;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PageExecutor implements Executor {

    private  static  String  pattern = "^.+ByQuery$";    // 需要进行分页操作的字符串正则表达式;
    private  Executor executor;
    public PageExecutor(Executor executor){
        this.executor = executor;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {
        return 0;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException {
        String msid = ms.getId();

        List<E> rows = executor.query(ms, parameter, rowBounds, resultHandler,
                cacheKey, boundSql);

        // 如果需要分页查询，修改返回类型为Page对象
        if (msid.matches(pattern)) {
            Configuration configuration = ms.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject = parameter == null ? null : configuration.newMetaObject(parameter);
            //System.out.println("分页数据总数2："+metaObject.getValue("pageTotal"));
            int offset = rowBounds.getOffset();
            int pagesize = rowBounds.getLimit();
            Page<E> page = (Page<E>) rows;
            return page;
        }
        return rows;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
        BoundSql boundSql = ms.getBoundSql(parameter);
        System.out.println("进入到了PageExecutor....query");
        return query(ms, parameter, rowBounds, resultHandler,
                executor.createCacheKey(ms, parameter, rowBounds, boundSql),
                boundSql);

    }

    @Override
    public <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException {
        return null;
    }

    @Override
    public List<BatchResult> flushStatements() throws SQLException {
        return null;
    }

    @Override
    public void commit(boolean required) throws SQLException {

    }

    @Override
    public void rollback(boolean required) throws SQLException {

    }

    @Override
    public CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql) {
        return null;
    }

    @Override
    public boolean isCached(MappedStatement ms, CacheKey key) {
        return false;
    }

    @Override
    public void clearLocalCache() {

    }

    @Override
    public void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType) {

    }

    @Override
    public Transaction getTransaction() {
        return null;
    }

    @Override
    public void close(boolean forceRollback) {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void setExecutorWrapper(Executor executor) {

    }
    /**
     * 获取总数
     * @param ms
     * @param parameter
     * @return
     */
    private int getCount(MappedStatement ms, Object parameter) {
        BoundSql bsql = ms.getBoundSql(parameter);
        String sql = bsql.getSql();
        String countSql = "SELECT COUNT(*) from " + sql;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = ms.getConfiguration().getEnvironment().getDataSource()
                    .getConnection();
            stmt = connection.prepareStatement(countSql);
            //setParameters(stmt, ms, bsql, parameter);
            rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

}
