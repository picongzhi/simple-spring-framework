package com.pcz.simple.spring.framework.context.support;

import com.pcz.simple.spring.framework.beans.factory.support.DefaultListableBeanFactory;
import com.pcz.simple.spring.framework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 抽象的 Xml 应用上下文
 *
 * @author picongzhi
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    /**
     * 获取配置路径
     *
     * @return 配置路径
     */
    protected abstract String[] getConfigLocations();
}
