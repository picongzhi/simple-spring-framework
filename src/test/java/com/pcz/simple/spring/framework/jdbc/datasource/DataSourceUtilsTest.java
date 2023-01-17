package com.pcz.simple.spring.framework.jdbc.datasource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceUtilsTest {
    @Test
    public void should_get_connection() throws SQLException {
        DataSource dataSource = Mockito.mock(DataSource.class);
        Connection connection = Mockito.mock(Connection.class);
        Mockito.when(dataSource.getConnection())
                .thenReturn(connection);

        Connection result = DataSourceUtils.getConnection(dataSource);
        Assertions.assertThat(result).isEqualTo(connection);
    }

    @Test
    public void should_throw_runtime_exception_to_get_connection_when_datasource_get_connection_throw_sql_exception() throws SQLException {
        DataSource dataSource = Mockito.mock(DataSource.class);
        Mockito.when(dataSource.getConnection())
                .thenThrow(new SQLException("获取连接失败"));

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> DataSourceUtils.getConnection(dataSource));
    }
}
