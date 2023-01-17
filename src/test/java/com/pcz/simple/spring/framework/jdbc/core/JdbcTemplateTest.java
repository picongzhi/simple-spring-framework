package com.pcz.simple.spring.framework.jdbc.core;

import cn.hutool.core.util.RandomUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class JdbcTemplateTest {
    private JdbcTemplate jdbcTemplate;

    private DataSource dataSource;

    private Connection connection;

    private Statement statement;

    @BeforeEach
    public void setUp() {
        dataSource = Mockito.mock(DataSource.class);
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(Statement.class);

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void should_execute() throws SQLException {
        Mockito.when(dataSource.getConnection())
                .thenReturn(connection);
        Mockito.when(connection.createStatement())
                .thenReturn(statement);

        StatementCallback<Object> callback = Mockito.mock(StatementCallback.class);
        Mockito.when(callback.doInStatement(statement))
                .thenReturn(new Object());

        Object result = jdbcTemplate.execute(callback);
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void should_execute_sql() throws SQLException {
        Mockito.when(dataSource.getConnection())
                .thenReturn(connection);
        Mockito.when(connection.createStatement())
                .thenReturn(statement);

        String sql = RandomUtil.randomString(20);
        Mockito.when(statement.execute(sql))
                .thenReturn(true);

        jdbcTemplate.execute(sql);
    }

    @Test
    public void should_query() throws SQLException {
        Mockito.when(dataSource.getConnection())
                .thenReturn(connection);
        Mockito.when(connection.createStatement())
                .thenReturn(statement);

        String sql = RandomUtil.randomString(20);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(statement.executeQuery(sql))
                .thenReturn(resultSet);

        ResultSetExtractor<Object> extractor = Mockito.mock(ResultSetExtractor.class);
        Mockito.when(extractor.extractData(resultSet))
                .thenReturn(new Object());

        Object result = jdbcTemplate.query(sql, extractor);
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void should_query_list() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        final int[] count = {0};
        Mockito.when(resultSet.next())
                .then((Answer<Boolean>) invocationOnMock -> {
                    if (count[0] < 3) {
                        count[0]++;
                        return true;
                    }

                    return false;
                });

        Mockito.when(dataSource.getConnection())
                .thenReturn(connection);
        Mockito.when(connection.createStatement())
                .thenReturn(statement);

        String sql = RandomUtil.randomString(20);
        Mockito.when(statement.executeQuery(sql))
                .thenReturn(resultSet);

        RowMapper<Object> rowMapper = Mockito.mock(RowMapper.class);
        Mockito.when(rowMapper.mapRow(ArgumentMatchers.any(), ArgumentMatchers.anyInt()))
                .thenReturn(new Object());

        List<Object> result = jdbcTemplate.query(sql, rowMapper);
        Assertions.assertThat(result).isNotEmpty();
    }

    @Test
    public void should_query_for_list() throws SQLException {
        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        int columnCount = 2;
        Mockito.when(resultSetMetaData.getColumnCount())
                .thenReturn(columnCount);
        Mockito.when(resultSetMetaData.getColumnLabel(ArgumentMatchers.anyInt()))
                .thenReturn(RandomUtil.randomString(10));

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        final int[] count = {0};
        Mockito.when(resultSet.next())
                .then((Answer<Boolean>) invocationOnMock -> {
                    if (count[0] < 3) {
                        count[0]++;
                        return true;
                    }

                    return false;
                });
        Mockito.when(resultSet.getMetaData())
                .thenReturn(resultSetMetaData);
        Mockito.when(resultSet.getObject(ArgumentMatchers.anyInt()))
                .thenReturn(new Object());

        Mockito.when(dataSource.getConnection())
                .thenReturn(connection);
        Mockito.when(connection.createStatement())
                .thenReturn(statement);

        String sql = RandomUtil.randomString(20);
        Mockito.when(statement.executeQuery(sql))
                .thenReturn(resultSet);

        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        Assertions.assertThat(result).isNotEmpty();
    }
}
