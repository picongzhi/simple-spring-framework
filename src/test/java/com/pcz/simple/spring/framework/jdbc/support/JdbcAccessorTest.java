package com.pcz.simple.spring.framework.jdbc.support;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;

public class JdbcAccessorTest {
    private JdbcAccessor jdbcAccessor;

    @BeforeEach
    public void setUp() {
        jdbcAccessor = new JdbcAccessor();
    }

    @Test
    public void should_set_datasource() {
        DataSource dataSource = Mockito.mock(DataSource.class);
        jdbcAccessor.setDataSource(dataSource);
    }

    @Test
    public void should_get_datasource() {
        DataSource result = jdbcAccessor.getDataSource();
        Assertions.assertThat(result).isNull();

        DataSource dataSource = Mockito.mock(DataSource.class);
        jdbcAccessor.setDataSource(dataSource);

        result = jdbcAccessor.getDataSource();
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void should_after_properties_set() {
        DataSource dataSource = Mockito.mock(DataSource.class);
        jdbcAccessor.setDataSource(dataSource);

        jdbcAccessor.afterPropertiesSet();
    }

    @Test
    public void should_throw_illegal_argument_exception_to_after_properties_set_when_datasource_is_null() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> jdbcAccessor.afterPropertiesSet());
    }

    @Test
    public void should_obtain_data_source() {
        DataSource dataSource = Mockito.mock(DataSource.class);
        jdbcAccessor.setDataSource(dataSource);

        DataSource result = jdbcAccessor.obtainDatasource();
        Assertions.assertThat(result).isEqualTo(dataSource);
    }
}
