package com.pcz.simple.spring.framework.jdbc.datasource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据源工具类
 *
 * @author picongzhi
 */
public class DataSourceUtils {
    public static Connection getConnection(DataSource dataSource) {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to obtain JDBC connection");
        }
    }
}
