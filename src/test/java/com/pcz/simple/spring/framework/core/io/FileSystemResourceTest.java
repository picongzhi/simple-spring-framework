package com.pcz.simple.spring.framework.core.io;

import cn.hutool.core.util.RandomUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileSystemResourceTest {
    @Test
    public void should_get_input_stream() throws IOException {
        // 通过path构造
        String path = "src/test/resources/important.properties";
        FileSystemResource fileSystemResource = new FileSystemResource(path);
        Assertions.assertThat(fileSystemResource).isNotNull();

        String filePath = fileSystemResource.getPath();
        Assertions.assertThat(filePath).isNotNull();

        InputStream inputStream = fileSystemResource.getInputStream();
        Assertions.assertThat(inputStream).isNotNull();

        // 通过File构造
        File file = new File(path);
        fileSystemResource = new FileSystemResource(file);
        Assertions.assertThat(fileSystemResource).isNotNull();

        filePath = fileSystemResource.getPath();
        Assertions.assertThat(filePath).isNotNull();

        inputStream = fileSystemResource.getInputStream();
        Assertions.assertThat(inputStream).isNotNull();
    }

    @Test
    public void should_throw_file_not_found_exception_to_get_input_stream_when_resource_not_exists() {
        String path = RandomUtil.randomString(10);
        FileSystemResource fileSystemResource = new FileSystemResource(path);
        Assertions.assertThat(fileSystemResource).isNotNull();

        Assertions.assertThatExceptionOfType(FileNotFoundException.class)
                .isThrownBy(fileSystemResource::getInputStream);
    }
}
