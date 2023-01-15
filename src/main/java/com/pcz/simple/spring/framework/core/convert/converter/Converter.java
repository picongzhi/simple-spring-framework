package com.pcz.simple.spring.framework.core.convert.converter;

/**
 * 类型转换器
 *
 * @param <S> 来源类型
 * @param <T> 目标类型
 * @author picongzhi
 */
public interface Converter<S, T> {
    /**
     * 将来源对象转换成目标对象
     *
     * @param source 来源对象
     * @return 目标对象
     */
    T convert(S source);
}
