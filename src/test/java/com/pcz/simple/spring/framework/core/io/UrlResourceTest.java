package com.pcz.simple.spring.framework.core.io;

import cn.hutool.core.util.RandomUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UrlResourceTest {
    @Test
    public void should_get_input_stream() throws IOException {
        URL url = new URL("https://www.baidu.com");
        UrlResource urlResource = new UrlResource(url);
        Assertions.assertThat(urlResource).isNotNull();

        InputStream inputStream = urlResource.getInputStream();
        Assertions.assertThat(inputStream).isNotNull();
    }

    @Test
    public void should_throw_io_exception_to_get_input_stream_when_url_not_found() throws IOException {
        URL url = new URL("https://" + RandomUtil.randomString(20));
        UrlResource urlResource = new UrlResource(url);
        Assertions.assertThat(urlResource).isNotNull();

        Assertions.assertThatExceptionOfType(IOException.class)
                .isThrownBy(urlResource::getInputStream);
    }
}
