package com.pcz.simple.spring.framework.jdbc.support;

import cn.hutool.core.util.RandomUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class JdbcUtilsTest {
    @Test
    public void should_lookup_column_name() throws SQLException {
        String label = RandomUtil.randomString(10);
        int index = RandomUtil.randomInt();

        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        Mockito.when(resultSetMetaData.getColumnLabel(index))
                .thenReturn(label);

        String name = JdbcUtils.lookupColumnName(resultSetMetaData, index);
        Assertions.assertThat(name).isEqualTo(label);
    }

    @Test
    public void should_lookup_column_name_when_label_is_empty() throws SQLException {
        int index = RandomUtil.randomInt();
        String name = RandomUtil.randomString(10);

        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        Mockito.when(resultSetMetaData.getColumnLabel(index))
                .thenReturn(null);
        Mockito.when(resultSetMetaData.getColumnName(index))
                .thenReturn(name);

        String result = JdbcUtils.lookupColumnName(resultSetMetaData, index);
        Assertions.assertThat(result).isEqualTo(name);
    }

    @Test
    public void should_get_result_set_value() throws SQLException {
        int index = RandomUtil.randomInt();
        String value = new String();

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getObject(index))
                .thenReturn(value);

        Object result = JdbcUtils.getResultSetValue(resultSet, index);
        Assertions.assertThat(result).isEqualTo(value);
    }
}
