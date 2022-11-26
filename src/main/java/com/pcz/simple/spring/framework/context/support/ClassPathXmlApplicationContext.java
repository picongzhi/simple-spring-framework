package com.pcz.simple.spring.framework.context.support;

/**
 * Classpath 下的 Xml 应用上下文
 *
 * @author picongzhi
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {
    /**
     * 配置路径
     */
    private String[] configLocations;

    public ClassPathXmlApplicationContext() {
    }

    public ClassPathXmlApplicationContext(String configLocation) {
        this(new String[]{configLocation});
    }

    public ClassPathXmlApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return this.configLocations;
    }
}
