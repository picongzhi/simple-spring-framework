package com.pcz.simple.spring.framework.core.convert.support;

import com.pcz.simple.spring.framework.core.convert.converter.Converter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringToNumberConverterFactoryTest {
    private StringToNumberConverterFactory converterFactory;

    @BeforeEach
    public void setUp() {
        converterFactory = new StringToNumberConverterFactory();
    }

    @Test
    public void should_get_converter() {
        Converter<String, Integer> converter = converterFactory.getConverter(Integer.class);
        Assertions.assertThat(converter).isNotNull();
    }

    @Test
    public void should_convert() {
        Converter<String, Integer> converter = converterFactory.getConverter(Integer.class);
        Assertions.assertThat(converter).isNotNull();

        String str = "123";
        Integer result = converter.convert(str);
        Assertions.assertThat(result).isEqualTo(123);
    }

    @Test
    public void should_return_null_to_convert_when_str_is_empty() {
        Converter<String, Integer> converter = converterFactory.getConverter(Integer.class);
        Assertions.assertThat(converter).isNotNull();

        String str = "";
        Integer result = converter.convert(str);
        Assertions.assertThat(result).isNull();
    }
}
