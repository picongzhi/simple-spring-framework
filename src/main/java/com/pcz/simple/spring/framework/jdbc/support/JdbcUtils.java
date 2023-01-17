package com.pcz.simple.spring.framework.jdbc.support;

import cn.hutool.core.util.StrUtil;

import java.sql.*;

/**
 * JDBC 工具类
 *
 * @author picongzhi
 */
public class JdbcUtils {
    /**
     * 从结果集元信息中获取列名
     * 先获取label，为空再获取name
     *
     * @param resultSetMetaData 结果集元信息
     * @param columnIndex       列索引
     * @return 列名
     * @throws SQLException SQL 异常
     */
    public static String lookupColumnName(ResultSetMetaData resultSetMetaData, int columnIndex)
            throws SQLException {
        String name = resultSetMetaData.getColumnLabel(columnIndex);
        if (StrUtil.isNotEmpty(name)) {
            return name;
        }

        return resultSetMetaData.getColumnName(columnIndex);
    }

    /**
     * 从结果集中获取列值
     *
     * @param resultSet 结果集
     * @param index     索引
     * @return 列值
     * @throws SQLException SQL 异常
     */
    public static Object getResultSetValue(ResultSet resultSet, int index) throws SQLException {
        Object value = resultSet.getObject(index);
        String className = value == null ?
                null : value.getClass().getName();

        if (value instanceof Blob) {
            Blob blob = (Blob) value;
            value = blob.getBytes(1, (int) blob.length());
        } else if (value instanceof Clob) {
            Clob clob = (Clob) value;
            value = clob.getSubString(1, (int) clob.length());
        } else if ("oracle.sql.TIMESTAMP".equals(className) || "oracle.sql.TIMESTAMPTZ".equals(className)) {
            value = resultSet.getTimestamp(index);
        } else if (className != null && className.startsWith("oracle.sql.DATE")) {
            String metadataClassname = resultSet.getMetaData().getColumnClassName(index);
            if ("java.sql.Timestamp".equals(metadataClassname) || "oracle.sql.TIMESTAMP".equals(metadataClassname)) {
                value = resultSet.getTimestamp(index);
            } else {
                value = resultSet.getDate(index);
            }
        } else if (value instanceof Date) {
            String metadataClassname = resultSet.getMetaData().getColumnClassName(index);
            if ("java.sql.Timestamp".equals(metadataClassname)) {
                value = resultSet.getDate(index);
            }
        }

        return value;
    }
}
