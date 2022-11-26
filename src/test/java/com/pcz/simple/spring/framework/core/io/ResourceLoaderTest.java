package com.pcz.simple.spring.framework.core.io;

import cn.hutool.core.io.IoUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ResourceLoaderTest {
    private ResourceLoader resourceLoader;

    @BeforeEach
    public void setUp() {
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void should_get_resource_from_class_path() throws IOException {
        String path = "classpath:important.properties";
        Resource resource = resourceLoader.getResource(path);
        String content = IoUtil.readUtf8(resource.getInputStream());
        Assertions.assertThat(content).isNotNull();
    }

    @Test
    public void should_get_resource_from_file_system() throws IOException {
        String path = "src/test/resources/important.properties";
        Resource resource = resourceLoader.getResource(path);
        String content = IoUtil.readUtf8(resource.getInputStream());
        Assertions.assertThat(content).isNotNull();
    }
}
