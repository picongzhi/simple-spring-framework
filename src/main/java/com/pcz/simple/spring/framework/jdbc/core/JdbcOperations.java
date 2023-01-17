package com.pcz.simple.spring.framework.jdbc.core;

import java.util.List;
import java.util.Map;

/**
 * JDBC 操作
 *
 * @author picongzhi
 */
public interface JdbcOperations {
    /**
     * 执行
     *
     * @param callback 回调
     * @param <T>      返回泛型
     * @return 执行结果
     * @throws Exception 执行异常
     */
    <T> T execute(StatementCallback<T> callback) throws Exception;

    /**
     * 执行 SQL
     *
     * @param sql SQL
     */
    void execute(String sql);

    /**
     * 查询
     *
     * @param sql                SQL
     * @param resultSetExtractor 结果集提取器
     * @param <T>                结果泛型
     * @return 查询结果
     */
    <T> T query(String sql, ResultSetExtractor<T> resultSetExtractor);

    /**
     * 查询集合
     *
     * @param sql       SQL
     * @param rowMapper 行映射
     * @param <T>       结果泛型
     * @return 查询结果
     */
    <T> List<T> query(String sql, RowMapper<T> rowMapper);

    /**
     * 查询集合
     *
     * @param sql SQL
     * @return 查询结果
     */
    List<Map<String, Object>> queryForList(String sql);
}
