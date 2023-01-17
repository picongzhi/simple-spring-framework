package com.pcz.simple.spring.framework.jdbc.core;

import cn.hutool.core.util.RandomUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

public class ColumnMapRowMapperTest {
    private ColumnMapRowMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new ColumnMapRowMapper();
    }

    @Test
    public void should_map_row() throws SQLException {
        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        String name = RandomUtil.randomString(10);
        Mockito.when(resultSetMetaData.getColumnLabel(ArgumentMatchers.anyInt()))
                .thenReturn(name);
        int columnCount = 2;
        Mockito.when(resultSetMetaData.getColumnCount())
                .thenReturn(columnCount);

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getMetaData())
                .thenReturn(resultSetMetaData);
        String value = RandomUtil.randomString(10);
        Mockito.when(resultSet.getObject(ArgumentMatchers.anyInt()))
                .thenReturn(value);

        int rowNum = 2;
        Map<String, Object> result = mapper.mapRow(resultSet, rowNum);
        Assertions.assertThat(result).isNotEmpty();
    }
}
