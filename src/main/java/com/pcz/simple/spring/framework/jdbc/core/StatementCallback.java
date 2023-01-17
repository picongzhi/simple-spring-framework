package com.pcz.simple.spring.framework.jdbc.core;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Statement 回调
 *
 * @param <T> 返回泛型
 * @author picongzhi
 */
public interface StatementCallback<T> {
    /**
     * 执行回调
     *
     * @param statement Statement
     * @return 回调结果
     * @throws SQLException SQL 异常
     */
    T doInStatement(Statement statement) throws SQLException;
}
