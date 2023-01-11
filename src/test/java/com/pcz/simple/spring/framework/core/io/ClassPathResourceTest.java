package com.pcz.simple.spring.framework.core.io;

import cn.hutool.core.util.RandomUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResourceTest {
    @Test
    public void should_get_input_stream() throws IOException {
        String path = "important.properties";
        ClassPathResource classPathResource = new ClassPathResource(path);
        Assertions.assertThat(classPathResource).isNotNull();

        InputStream inputStream = classPathResource.getInputStream();
        Assertions.assertThat(inputStream).isNotNull();
    }

    @Test
    public void should_throw_file_not_found_exception_to_get_input_stream_when_resource_not_exists() {
        String path = RandomUtil.randomString(10);
        ClassPathResource classPathResource = new ClassPathResource(path);
        Assertions.assertThat(classPathResource).isNotNull();

        Assertions.assertThatExceptionOfType(FileNotFoundException.class)
                .isThrownBy(classPathResource::getInputStream);
    }
}
