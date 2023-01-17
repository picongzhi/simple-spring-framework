package com.pcz.simple.spring.framework.jdbc.core;

import cn.hutool.core.util.RandomUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RowMapperResultSetExtractorTest {
    private RowMapperResultSetExtractor<String> extractor;

    private RowMapper<String> rowMapper;

    @BeforeEach
    public void setUp() {
        rowMapper = Mockito.mock(RowMapper.class);
        extractor = new RowMapperResultSetExtractor<String>(rowMapper);
    }

    @Test
    public void should_extract_data() throws SQLException {
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

        Mockito.when(rowMapper.mapRow(ArgumentMatchers.any(), ArgumentMatchers.anyInt()))
                .thenReturn(RandomUtil.randomString(10));

        List<String> result = extractor.extractData(resultSet);
        Assertions.assertThat(result).isNotEmpty();
    }
}
