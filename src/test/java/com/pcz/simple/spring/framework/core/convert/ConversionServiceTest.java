package com.pcz.simple.spring.framework.core.convert;

import com.pcz.simple.spring.framework.core.convert.converter.Converter;
import com.pcz.simple.spring.framework.core.convert.converter.ConverterFactory;
import com.pcz.simple.spring.framework.core.convert.converter.GenericConverter;
import com.pcz.simple.spring.framework.core.convert.support.DefaultConversionService;
import com.pcz.simple.spring.framework.core.convert.support.GenericConversionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

public class ConversionServiceTest {
    private GenericConversionService conversionService;

    @BeforeEach
    public void setUp() {
        conversionService = new DefaultConversionService();
    }

    @Test
    public void should_add_converter() {
        Converter<String, Integer> converter = new Converter<String, Integer>() {
            @Override
            public Integer convert(String source) {
                return Integer.parseInt(source);
            }
        };
        conversionService.addConverter(converter);
    }

    @Test
    public void should_add_generic_converter() {
        GenericConverter genericConverter = new GenericConverter() {
            @Override
            public Set<ConvertiblePair> getConvertibleTypes() {
                return Collections.singleton(new ConvertiblePair(String.class, Integer.class));
            }

            @Override
            public Object convert(Object source, Class<?> sourceType, Class<?> targetType) {
                return new Converter<String, Integer>() {
                    @Override
                    public Integer convert(String source) {
                        return null;
                    }
                };
            }
        };
        conversionService.addConverter(genericConverter);
    }

    @Test
    public void should_add_converter_factory() {
        ConverterFactory<String, Integer> converterFactory = new ConverterFactory<String, Integer>() {
            @Override
            public <T extends Integer> Converter<String, T> getConverter(Class<T> targetClass) {
                return null;
            }
        };
        conversionService.addConverterFactory(converterFactory);
    }

    @Test
    public void should_can_convert() {
        boolean canConvert = conversionService.canConvert(String.class, Integer.class);
        Assertions.assertThat(canConvert).isTrue();
    }

    @Test
    public void should_not_can_convert() {
        boolean canConvert = conversionService.canConvert(Integer.class, String.class);
        Assertions.assertThat(canConvert).isFalse();
    }

    @Test
    public void should_convert() {
        String source = "123";
        Integer target = conversionService.convert(source, Integer.class);
        Assertions.assertThat(target).isEqualTo(123);
    }

    @Test
    public void should_convert_when_source_type_is_integer_and_target_type_is_string() {
        conversionService.addConverter(new Converter<Integer, String>() {
            @Override
            public String convert(Integer source) {
                return String.valueOf(source);
            }
        });

        Integer source = 123;
        String target = conversionService.convert(source, String.class);
        Assertions.assertThat(target).isEqualTo("123");
    }
}
