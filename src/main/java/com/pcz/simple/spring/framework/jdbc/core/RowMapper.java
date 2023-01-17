package com.pcz.simple.spring.framework.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Row 结果映射器
 *
 * @param <T> 结果泛型
 * @author picongzhi
 */
public interface RowMapper<T> {
    /**
     * 映射结果
     *
     * @param resultSet 结果集
     * @param rowNum    行数
     * @return 映射后的结果
     * @throws SQLException SQL 异常
     */
    T mapRow(ResultSet resultSet, int rowNum) throws SQLException;
}
