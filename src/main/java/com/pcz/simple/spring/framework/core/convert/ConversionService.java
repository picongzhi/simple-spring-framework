package com.pcz.simple.spring.framework.core.convert;

import com.sun.istack.internal.Nullable;

/**
 * 类型转换服务
 *
 * @author picongzhi
 */
public interface ConversionService {
    /**
     * 判断是否可以从来源类型转换成目标类型
     *
     * @param sourceType  来源类型
     * @param targetClass 目标类型
     * @return 是否可以转换
     */
    boolean canConvert(@Nullable Class<?> sourceType, Class<?> targetClass);

    /**
     * 类型转换
     *
     * @param source     来源对象
     * @param targetType 目标类型
     * @param <T>        目标类型
     * @return 目标对象
     */
    <T> T convert(Object source, Class<T> targetType);
}
