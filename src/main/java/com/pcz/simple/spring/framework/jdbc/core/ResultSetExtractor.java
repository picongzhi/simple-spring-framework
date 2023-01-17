package com.pcz.simple.spring.framework.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 结果集提取器
 *
 * @param <T> 结果泛型
 * @author picongzhi
 */
public interface ResultSetExtractor<T> {
    /**
     * 提取结果
     *
     * @param resultSet 结果集
     * @return 结果
     * @throws SQLException SQL 异常
     */
    T extractData(ResultSet resultSet) throws SQLException;
}
