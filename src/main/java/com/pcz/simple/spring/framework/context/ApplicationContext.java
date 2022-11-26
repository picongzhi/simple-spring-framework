package com.pcz.simple.spring.framework.context;

import com.pcz.simple.spring.framework.beans.factory.HierarchicalBeanFactory;
import com.pcz.simple.spring.framework.beans.factory.ListableBeanFactory;
import com.pcz.simple.spring.framework.core.io.ResourceLoader;

/**
 * 应用上下文
 *
 * @author picongzhi
 */
public interface ApplicationContext
        extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
