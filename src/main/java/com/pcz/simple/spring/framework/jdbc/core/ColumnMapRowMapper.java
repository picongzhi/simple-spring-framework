package com.pcz.simple.spring.framework.jdbc.core;

import com.pcz.simple.spring.framework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 列Map Row 结果映射器
 * 将结果按照 key 为列，value 为值的格式返回
 *
 * @author picongzhi
 */
public class ColumnMapRowMapper implements RowMapper<Map<String, Object>> {
    @Override
    public Map<String, Object> mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();

        Map<String, Object> columnMap = createColumnMap(columnCount);
        for (int i = 0; i <= columnCount; i++) {
            String columnName = JdbcUtils.lookupColumnName(resultSetMetaData, i);
            columnMap.putIfAbsent(getColumnKey(columnName), getColumnValue(resultSet, i));
        }

        return columnMap;
    }

    /**
     * 创建列 Map
     *
     * @param columnCount 列数
     * @return 列 Map
     */
    protected Map<String, Object> createColumnMap(int columnCount) {
        return new LinkedHashMap<>(columnCount);
    }

    /**
     * 获取列 key
     *
     * @param columnName 列名
     * @return 列 key
     */
    protected String getColumnKey(String columnName) {
        return columnName;
    }

    /**
     * 获取列值
     *
     * @param resultSet 结果集
     * @param index     列索引
     * @return 列值
     */
    protected Object getColumnValue(ResultSet resultSet, int index) throws SQLException {
        return JdbcUtils.getResultSetValue(resultSet, index);
    }
}
