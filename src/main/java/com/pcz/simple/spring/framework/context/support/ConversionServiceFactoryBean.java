package com.pcz.simple.spring.framework.context.support;

import com.pcz.simple.spring.framework.beans.factory.FactoryBean;
import com.pcz.simple.spring.framework.beans.factory.InitializingBean;
import com.pcz.simple.spring.framework.core.convert.ConversionService;
import com.pcz.simple.spring.framework.core.convert.converter.Converter;
import com.pcz.simple.spring.framework.core.convert.converter.ConverterFactory;
import com.pcz.simple.spring.framework.core.convert.converter.ConverterRegistry;
import com.pcz.simple.spring.framework.core.convert.converter.GenericConverter;
import com.pcz.simple.spring.framework.core.convert.support.DefaultConversionService;
import com.pcz.simple.spring.framework.core.convert.support.GenericConversionService;
import com.sun.istack.internal.Nullable;

import java.util.Set;

/**
 * 类型转换服务 FactoryBean
 *
 * @author picongzhi
 */
public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {
    /**
     * 类型转换器
     * {@link Converter}
     * {@link GenericConverter}
     * {@link ConverterFactory}
     */
    @Nullable
    private Set<?> converters;

    /**
     * 类型转换服务
     */
    @Nullable
    private GenericConversionService conversionService;

    @Override
    public ConversionService getObject() throws Exception {
        return this.conversionService;
    }

    @Override
    public Class<?> getObjectType() {
        return this.conversionService.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.conversionService = new DefaultConversionService();
        registerConverters(converters, conversionService);
    }

    /**
     * 设置转换器
     *
     * @param converters 转换器
     */
    public void setConverters(Set<?> converters) {
        this.converters = converters;
    }

    /**
     * 向类型转换服务注册类型转换器
     *
     * @param converters        类型转换器
     * @param converterRegistry 类型转换器注册器
     */
    private void registerConverters(Set<?> converters, ConverterRegistry converterRegistry) {
        if (converters == null) {
            return;
        }

        for (Object converter : converters) {
            if (converter instanceof GenericConverter) {
                converterRegistry.addConverter((GenericConverter) converter);
            } else if (converter instanceof Converter<?, ?>) {
                converterRegistry.addConverter((Converter<?, ?>) converter);
            } else if (converter instanceof ConverterFactory<?, ?>) {
                converterRegistry.addConverterFactory((ConverterFactory<?, ?>) converter);
            } else {
                throw new IllegalArgumentException("Each converter object must implement one of the "
                        + " Converter, ConverterFactory or GenericConverter interface");
            }
        }
    }
}
