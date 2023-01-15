package com.pcz.simple.spring.framework.core.convert.support;

import com.pcz.simple.spring.framework.core.convert.ConversionService;
import com.pcz.simple.spring.framework.core.convert.converter.Converter;
import com.pcz.simple.spring.framework.core.convert.converter.ConverterFactory;
import com.pcz.simple.spring.framework.core.convert.converter.ConverterRegistry;
import com.pcz.simple.spring.framework.core.convert.converter.GenericConverter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 通用的类型转换服务
 *
 * @author picongzhi
 */
public class GenericConversionService implements ConversionService, ConverterRegistry {
    /**
     * 类型转换器映射
     * 来源目标类型对 -> 转换器
     */
    private Map<GenericConverter.ConvertiblePair, GenericConverter> converters = new HashMap<>();

    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetClass) {
        GenericConverter converter = getConverter(sourceType, targetClass);
        return converter != null;
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        Class<?> sourceType = source.getClass();
        GenericConverter converter = getConverter(sourceType, targetType);
        return (T) converter.convert(source, sourceType, targetType);
    }

    @Override
    public void addConverter(Converter<?, ?> converter) {
        GenericConverter.ConvertiblePair convertiblePair = getConvertiblePair(converter);
        ConverterAdapter converterAdapter = new ConverterAdapter(convertiblePair, converter);
        for (GenericConverter.ConvertiblePair pair : converterAdapter.getConvertibleTypes()) {
            this.converters.put(pair, converterAdapter);
        }
    }

    @Override
    public void addConverter(GenericConverter converter) {
        for (GenericConverter.ConvertiblePair pair : converter.getConvertibleTypes()) {
            this.converters.put(pair, converter);
        }
    }

    @Override
    public void addConverterFactory(ConverterFactory<?, ?> converterFactory) {
        GenericConverter.ConvertiblePair convertiblePair = getConvertiblePair(converterFactory);
        ConverterFactoryAdapter converterFactoryAdapter = new ConverterFactoryAdapter(convertiblePair, converterFactory);
        for (GenericConverter.ConvertiblePair pair : converterFactoryAdapter.getConvertibleTypes()) {
            this.converters.put(pair, converterFactoryAdapter);
        }
    }

    /**
     * 根据来源类型和目标类型
     *
     * @param sourceType 来源类型
     * @param targetType 目标类型
     * @return 类型转换器
     */
    protected GenericConverter getConverter(Class<?> sourceType, Class<?> targetType) {
        List<Class<?>> sourceCandidates = getClassHierarchy(sourceType);
        List<Class<?>> targetCandidates = getClassHierarchy(targetType);
        for (Class<?> sourceCandidate : sourceCandidates) {
            for (Class<?> targetCandidate : targetCandidates) {
                GenericConverter.ConvertiblePair convertiblePair =
                        new GenericConverter.ConvertiblePair(sourceCandidate, targetCandidate);
                GenericConverter converter = this.converters.get(convertiblePair);
                if (converter != null) {
                    return converter;
                }
            }
        }

        return null;
    }

    /**
     * 层级遍历获取类及其父类
     *
     * @param cls 类
     * @return 层级遍历所有的类
     */
    private List<Class<?>> getClassHierarchy(Class<?> cls) {
        List<Class<?>> hierarchy = new ArrayList<>();
        while (cls != null) {
            hierarchy.add(cls);
            cls = cls.getSuperclass();
        }

        return hierarchy;
    }

    /**
     * 获取转换器类型对
     * 获取对象的泛型类型，并封装成转换类型对
     *
     * @param object 对象
     * @return 转换器类型对
     */
    private GenericConverter.ConvertiblePair getConvertiblePair(Object object) {
        // 获取类型转换器的泛型
        Type[] types = object.getClass().getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) types[0];

        // 获取来源类型和目标类型
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Class<?> sourceType = (Class<?>) actualTypeArguments[0];
        Class<?> targetType = (Class<?>) actualTypeArguments[1];

        return new GenericConverter.ConvertiblePair(sourceType, targetType);
    }

    /**
     * 转换器适配器
     */
    private final class ConverterAdapter implements GenericConverter {
        /**
         * 类型转换对
         */
        private final ConvertiblePair convertiblePair;

        /**
         * 转换器
         */
        private final Converter<Object, Object> converter;

        public ConverterAdapter(ConvertiblePair convertiblePair,
                                Converter<?, ?> converter) {
            this.convertiblePair = convertiblePair;
            this.converter = (Converter<Object, Object>) converter;
        }

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(this.convertiblePair);
        }

        @Override
        public Object convert(Object source, Class<?> sourceType, Class<?> targetType) {
            return this.converter.convert(source);
        }
    }

    /**
     * 转换器工厂适配器
     */
    private final class ConverterFactoryAdapter implements GenericConverter {
        /**
         * 转换类型对
         */
        private final ConvertiblePair convertiblePair;

        /**
         * 转换器工厂
         */
        private final ConverterFactory<Object, Object> converterFactory;

        public ConverterFactoryAdapter(ConvertiblePair convertiblePair,
                                       ConverterFactory<?, ?> converterFactory) {
            this.convertiblePair = convertiblePair;
            this.converterFactory = (ConverterFactory<Object, Object>) converterFactory;
        }

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(this.convertiblePair);
        }

        @Override
        public Object convert(Object source, Class<?> sourceType, Class<?> targetType) {
            return this.converterFactory.getConverter(targetType).convert(source);
        }
    }
}
