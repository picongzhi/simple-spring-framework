package com.pcz.simple.spring.framework.aop.framework;

import com.pcz.simple.spring.framework.aop.AdvisedSupport;

/**
 * 代理工厂
 *
 * @author picongzhi
 */
public class ProxyFactory {
    /**
     * 通知辅助
     */
    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    /**
     * 创建 AopProxy
     *
     * @return AopProxy
     */
    private AopProxy createAopProxy() {
        return advisedSupport.isProxyTargetClass() ?
                new Cglib2AopProxy(advisedSupport) :
                new JdkDynamicAopProxy(advisedSupport);
    }
}
