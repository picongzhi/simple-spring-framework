package com.pcz.simple.spring.framework.jdbc.core;

import cn.hutool.core.lang.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * RowMapper 结果提取器
 *
 * @param <T> 返回泛型
 * @author picongzhi
 */
public class RowMapperResultSetExtractor<T> implements ResultSetExtractor<List<T>> {
    /**
     * 行映射器
     */
    private final RowMapper<T> rowMapper;

    /**
     * 预期行数
     */
    private final int rowsExpected;

    public RowMapperResultSetExtractor(RowMapper<T> rowMapper) {
        this(rowMapper, 0);
    }

    public RowMapperResultSetExtractor(RowMapper<T> rowMapper, int rowsExpected) {
        Assert.notNull(rowMapper, "RowMapper is required");
        this.rowMapper = rowMapper;
        this.rowsExpected = rowsExpected;
    }

    @Override
    public List<T> extractData(ResultSet resultSet) throws SQLException {
        List<T> results = this.rowsExpected > 0 ?
                new ArrayList<>(this.rowsExpected) : new ArrayList<>();

        int rowNum = 0;
        while (resultSet.next()) {
            results.add(this.rowMapper.mapRow(resultSet, rowNum++));
        }

        return results;
    }
}
