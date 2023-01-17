package com.pcz.simple.spring.framework.jdbc.core;

import cn.hutool.core.lang.Assert;
import com.pcz.simple.spring.framework.jdbc.datasource.DataSourceUtils;
import com.pcz.simple.spring.framework.jdbc.support.JdbcAccessor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * JDBC template
 *
 * @author picongzhi
 */
public class JdbcTemplate extends JdbcAccessor implements JdbcOperations {
    /**
     * 获取数量
     */
    private int fetchSize = -1;

    /**
     * 最大行数
     */
    private int maxRows = -1;

    /**
     * 查询超时时间
     */
    private int queryTimeout = -1;

    public JdbcTemplate() {
    }

    public JdbcTemplate(DataSource dataSource) {
        setDataSource(dataSource);
        afterPropertiesSet();
    }

    public int getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public int getQueryTimeout() {
        return queryTimeout;
    }

    public void setQueryTimeout(int queryTimeout) {
        this.queryTimeout = queryTimeout;
    }

    @Override
    public <T> T execute(StatementCallback<T> callback) {
        Connection connection = DataSourceUtils.getConnection(obtainDatasource());
        try {
            Statement statement = connection.createStatement();
            applyStatementSettings(statement);

            return callback.doInStatement(statement);
        } catch (SQLException e) {
            throw new RuntimeException("StatementCallback", e);
        }
    }

    @Override
    public void execute(String sql) {
        class ExecuteStatementCallback implements StatementCallback<Object>, SqlProvider {
            @Override
            public Object doInStatement(Statement statement) throws SQLException {
                statement.execute(sql);
                return null;
            }

            @Override
            public String getSql() {
                return sql;
            }
        }

        execute(new ExecuteStatementCallback());
    }

    @Override
    public <T> T query(String sql, ResultSetExtractor<T> resultSetExtractor) {
        class QueryStatementCallback implements StatementCallback<T>, SqlProvider {
            @Override
            public T doInStatement(Statement statement) throws SQLException {
                ResultSet resultSet = statement.executeQuery(sql);
                return resultSetExtractor.extractData(resultSet);
            }

            @Override
            public String getSql() {
                return sql;
            }
        }

        return execute(new QueryStatementCallback());
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> rowMapper) {
        return result(query(sql, new RowMapperResultSetExtractor<>(rowMapper)));
    }

    @Override
    public List<Map<String, Object>> queryForList(String sql) {
        return query(sql, new ColumnMapRowMapper());
    }

    private static <T> T result(T result) {
        Assert.state(result != null, "No result");
        return result;
    }

    /**
     * 应用配置
     *
     * @param statement Statement
     * @throws SQLException SQL 异常
     */
    protected void applyStatementSettings(Statement statement) throws SQLException {
        int fetchSize = getFetchSize();
        if (fetchSize != -1) {
            statement.setFetchSize(fetchSize);
        }

        int maxRows = getMaxRows();
        if (maxRows != -1) {
            statement.setMaxRows(maxRows);
        }
    }
}
