package com.pcz.simple.spring.framework.jdbc.core;

/**
 * SQL provider
 *
 * @author picongzhi
 */
public interface SqlProvider {
    /**
     * 获取 SQL
     *
     * @return SQL
     */
    String getSql();
}
