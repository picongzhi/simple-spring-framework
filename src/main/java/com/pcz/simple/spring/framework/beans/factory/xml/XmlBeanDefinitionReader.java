package com.pcz.simple.spring.framework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.PropertyValue;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import com.pcz.simple.spring.framework.beans.factory.config.BeanReference;
import com.pcz.simple.spring.framework.beans.factory.support.AbstractBeanDefinitionReader;
import com.pcz.simple.spring.framework.beans.factory.support.BeanDefinitionRegistry;
import com.pcz.simple.spring.framework.context.annotation.ClassPathBeanDefinitionScanner;
import com.pcz.simple.spring.framework.core.io.Resource;
import com.pcz.simple.spring.framework.core.io.ResourceLoader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Xml BeanDefinition Reader
 *
 * @author picongzhi
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            try (InputStream inputStream = resource.getInputStream()) {
                doLoadBeanDefinitions(inputStream);
            }
        } catch (IOException | ClassNotFoundException | DocumentException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        Resource resource = getResourceLoader().getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, DocumentException {
        SAXReader reader = new SAXReader();

        Document document = reader.read(inputStream);
        Element root = document.getRootElement();

        Element componentScan = root.element("component-scan");
        if (componentScan != null) {
            String backPackage = componentScan.attributeValue("base-package");
            if (StrUtil.isEmpty(backPackage)) {
                throw new BeansException("The value of base-package attribute can not be empty or null");
            }

            // ????????????
            scanPackage(backPackage);
        }

        List<Element> beans = root.elements("bean");
        for (Element bean : beans) {
            String id = bean.attributeValue("id");
            String name = bean.attributeValue("name");
            String className = bean.attributeValue("class");
            String initMethod = bean.attributeValue("init-method");
            String destroyMethod = bean.attributeValue("destroy-method");
            String scope = bean.attributeValue("scope");

            // ?????? bean class
            Class<?> beanClass = Class.forName(className);

            // ?????? bean ??????
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(beanClass.getSimpleName());
            }

            // ?????? BeanDefinition
            BeanDefinition beanDefinition = new BeanDefinition(beanClass);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethod);

            if (StrUtil.isNotEmpty(scope)) {
                beanDefinition.setScope(scope);
            }

            List<Element> properties = bean.elements("property");
            for (Element property : properties) {
                // ????????????
                String propName = property.attributeValue("name");

                // ?????????
                String propValue = property.attributeValue("value");
                String propRef = property.attributeValue("ref");
                Object value = StrUtil.isNotEmpty(propRef) ?
                        new BeanReference(propRef) : propValue;

                PropertyValue propertyValue = new PropertyValue(propName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }

            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }

            // ?????? BeanDefinition
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }

//        Document document = XmlUtil.readXML(inputStream);
//        Element root = document.getDocumentElement();
//        NodeList childNodes = root.getChildNodes();
//
//        for (int i = 0; i < childNodes.getLength(); i++) {
//            Node childNode = childNodes.item(i);
//            if (!(childNode instanceof Element)) {
//                // ???????????? XML ???????????????
//                continue;
//            }
//
//            if (!"bean".equals(childNode.getNodeName())) {
//                // ?????????????????? bean?????????
//                continue;
//            }
//
//            Element beanElement = (Element) childNode;
//
//            String id = beanElement.getAttribute("id");
//            String name = beanElement.getAttribute("name");
//            String className = beanElement.getAttribute("class");
//            String initMethodName = beanElement.getAttribute("init-method");
//            String destroyMethodName = beanElement.getAttribute("destroyMethod");
//            String scope = beanElement.getAttribute("scope");
//
//            Class<?> beanClass = Class.forName(className);
//            String beanName = StrUtil.isNotEmpty(id) ? id : name;
//            if (StrUtil.isEmpty(beanName)) {
//                beanName = StrUtil.lowerFirst(beanClass.getSimpleName());
//            }
//
//            BeanDefinition beanDefinition = new BeanDefinition(beanClass);
//            beanDefinition.setInitMethodName(initMethodName);
//            beanDefinition.setDestroyMethodName(destroyMethodName);
//
//            if (StrUtil.isNotEmpty(scope)) {
//                beanDefinition.setScope(scope);
//            }
//
//            NodeList beanNodeList = beanElement.getChildNodes();
//            for (int j = 0; j < beanNodeList.getLength(); j++) {
//                Node beanChildNode = beanNodeList.item(j);
//                if (!(beanChildNode instanceof Element)) {
//                    // ??????????????? XML ???????????????
//                    continue;
//                }
//
//                if (!"property".equals(beanChildNode.getNodeName())) {
//                    // ??????????????? property?????????
//                    continue;
//                }
//
//                Element propertyElement = (Element) beanChildNode;
//
//                String attrName = propertyElement.getAttribute("name");
//                String attrValue = propertyElement.getAttribute("value");
//                String attrRef = propertyElement.getAttribute("ref");
//
//                Object value = StrUtil.isNotEmpty(attrRef) ?
//                        new BeanReference(attrRef) :
//                        attrValue;
//                PropertyValue propertyValue = new PropertyValue(attrName, value);
//                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
//            }
//
//            if (getRegistry().containsBeanDefinition(beanName)) {
//                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
//            }
//
//            getRegistry().registerBeanDefinition(beanName, beanDefinition);
//        }
    }

    /**
     * ???????????????
     *
     * @param basePackage ?????????
     */
    private void scanPackage(String basePackage) {
        String[] basePackages = StrUtil.splitToArray(basePackage, ',');
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.doScan(basePackages);
    }
}
