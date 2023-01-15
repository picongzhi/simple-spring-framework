package com.pcz.simple.spring.framework.core.convert.support;

import com.pcz.simple.spring.framework.core.convert.converter.Converter;
import com.pcz.simple.spring.framework.core.convert.converter.ConverterFactory;
import com.pcz.simple.spring.framework.util.NumberUtils;

/**
 * 字符串类型转数字类型转换器工厂
 *
 * @author picongzhi
 */
public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {
    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetClass) {
        return new StringToNumber<>(targetClass);
    }

    /**
     * String 转 Number 转换器
     *
     * @param <T> 目标类型泛型
     */
    private static final class StringToNumber<T extends Number> implements Converter<String, T> {
        /**
         * 目标类型
         */
        private final Class<T> targetClass;

        public StringToNumber(Class<T> targetClass) {
            this.targetClass = targetClass;
        }

        @Override
        public T convert(String source) {
            if (source.isEmpty()) {
                return null;
            }

            return NumberUtils.parseNumber(source, this.targetClass);
        }
    }
}
