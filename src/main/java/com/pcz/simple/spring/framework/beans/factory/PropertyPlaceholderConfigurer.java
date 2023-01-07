package com.pcz.simple.spring.framework.beans.factory;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.PropertyValue;
import com.pcz.simple.spring.framework.beans.PropertyValues;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import com.pcz.simple.spring.framework.beans.factory.config.BeanFactoryPostProcessor;
import com.pcz.simple.spring.framework.core.io.DefaultResourceLoader;
import com.pcz.simple.spring.framework.core.io.Resource;

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
    private String localtion;

    public void setLocaltion(String localtion) {
        this.localtion = localtion;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(localtion);

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

                String strValue = (String) value;
                StringBuilder stringBuilder = new StringBuilder(strValue);

                int startIndex = strValue.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                int stopIndex = strValue.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                if (startIndex != -1 && stopIndex != -1 && startIndex < stopIndex) {
                    String propKey = strValue.substring(startIndex + 2, stopIndex);
                    String propValue = properties.getProperty(propKey);
                    stringBuilder.replace(startIndex, stopIndex + 1, propValue);

                    propertyValues.addPropertyValue(
                            new PropertyValue(propertyValue.getName(), stringBuilder.toString()));
                }
            }
        }
    }
}
