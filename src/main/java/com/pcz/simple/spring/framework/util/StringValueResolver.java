package com.pcz.simple.spring.framework.util;

/**
 * 字符串值解析器
 *
 * @author picongzhi
 */
public interface StringValueResolver {
    /**
     * 解析字符串值
     *
     * @param value 字符串值
     * @return 解析后的结果
     */
    String resolveStringValue(String value);
}
