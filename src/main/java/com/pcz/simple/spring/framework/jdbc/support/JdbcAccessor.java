package com.pcz.simple.spring.framework.jdbc.support;

import cn.hutool.core.lang.Assert;
import com.pcz.simple.spring.framework.beans.factory.InitializingBean;

import javax.sql.DataSource;

/**
 * JDBC Accessor
 *
 * @author picongzhi
 */
public class JdbcAccessor implements InitializingBean {
    /**
     * 数据源
     */
    private DataSource dataSource;

    @Override
    public void afterPropertiesSet() {
        if (getDataSource() == null) {
            throw new IllegalArgumentException("Property 'datasource' is required");
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取数据源
     *
     * @return 数据源
     */
    protected DataSource obtainDatasource() {
        DataSource dataSource = getDataSource();
        Assert.state(dataSource != null, "No datasource set");

        return dataSource;
    }
}
