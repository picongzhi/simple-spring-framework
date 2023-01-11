package com.pcz.simple.spring.framework.beans.factory;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.PropertyValue;
import com.pcz.simple.spring.framework.beans.PropertyValues;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import com.pcz.simple.spring.framework.beans.factory.config.BeanFactoryPostProcessor;
import com.pcz.simple.spring.framework.core.io.DefaultResourceLoader;
import com.pcz.simple.spring.framework.core.io.Resource;
import com.pcz.simple.spring.framework.util.StringValueResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * 属性占位符配置器
 *
 * @author picongzhi
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {
    /**
     * 默认的占位符前缀
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    /**
     * 默认的占位符后缀
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    /**
     * 属性文件路径
     */
    private String location;

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(location);

        // 加载属性文件
        Properties properties = new Properties();
        try {
            properties.load(resource.getInputStream());
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }

        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);

            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                Object value = propertyValue.getValue();
                if (!(value instanceof String)) {
                    continue;
                }

                value = resolvePlaceholder((String) value, properties);
                propertyValues.addPropertyValue(
                        new PropertyValue(propertyValue.getName(), value));
            }
        }

        // 向容器添加字符串占位符解析器
        StringValueResolver resolver = new PlaceholderResolvingStringValueResolver(properties);
        beanFactory.addEmbeddedValueResolver(resolver);
    }

    /**
     * 解析字符串占位符
     *
     * @param value      字符串
     * @param properties 配置文件
     * @return 解析后的值
     */
    private String resolvePlaceholder(String value, Properties properties) {
        StringBuilder stringBuilder = new StringBuilder(value);

        int startIndex = value.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        int stopIndex = value.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
        if (startIndex != -1 && stopIndex != -1 && startIndex < stopIndex) {
            String propKey = value.substring(startIndex + 2, stopIndex);
            String propValue = properties.getProperty(propKey);
            stringBuilder.replace(startIndex, stopIndex + 1, propValue);
        }

        return stringBuilder.toString();
    }

    /**
     * 占位符字符串解析器
     */
    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {
        /**
         * 配置
         */
        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String resolveStringValue(String value) {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(value, properties);
        }
    }
}
