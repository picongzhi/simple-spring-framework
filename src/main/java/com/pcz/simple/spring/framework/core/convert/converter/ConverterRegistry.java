package com.pcz.simple.spring.framework.core.convert.converter;

/**
 * 类型转换器注册器
 *
 * @author picongzhi
 */
public interface ConverterRegistry {
    /**
     * 添加类型转换器
     *
     * @param converter 类型转换器
     */
    void addConverter(Converter<?, ?> converter);

    /**
     * 添加通用的类型转换器
     *
     * @param converter 通用的类型转换器
     */
    void addConverter(GenericConverter converter);

    /**
     * 添加类型转换器工厂
     *
     * @param converterFactory 类型转换器工厂
     */
    void addConverterFactory(ConverterFactory<?, ?> converterFactory);
}
