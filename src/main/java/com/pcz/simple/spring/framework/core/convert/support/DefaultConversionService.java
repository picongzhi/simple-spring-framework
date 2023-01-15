package com.pcz.simple.spring.framework.core.convert.support;

import com.pcz.simple.spring.framework.core.convert.converter.ConverterRegistry;

/**
 * 默认的类型转换服务
 *
 * @author picongzhi
 */
public class DefaultConversionService extends GenericConversionService {
    public DefaultConversionService() {
        addDefaultConverters(this);
    }

    /**
     * 注册默认的类型转换器
     *
     * @param converterRegistry 类型转换器注册器
     */
    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
    }
}
