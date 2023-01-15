package com.pcz.simple.spring.framework.core.convert.converter;

/**
 * 类型转换器工厂
 *
 * @param <S> 来源类型
 * @param <R> 目标类型
 * @author picongzhi
 */
public interface ConverterFactory<S, R> {
    /**
     * 获取类型转换器
     *
     * @param targetClass 目标类
     * @param <T>         目标类型
     * @return 类型转换器
     */
    <T extends R> Converter<S, T> getConverter(Class<T> targetClass);
}
