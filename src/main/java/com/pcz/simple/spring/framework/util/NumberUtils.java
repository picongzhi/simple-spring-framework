package com.pcz.simple.spring.framework.util;

import cn.hutool.core.lang.Assert;
import com.sun.istack.internal.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Number 工具类
 *
 * @author picongzhi
 */
public class NumberUtils {
    /**
     * Long型最小值
     */
    private static final BigInteger LONG_MIN = BigInteger.valueOf(Long.MIN_VALUE);

    /**
     * Long型最大值
     */
    private static final BigInteger LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);

    /**
     * 标准Number类型集合
     */
    public static final Set<Class<?>> STANDARD_NUMBER_TYPES;

    static {
        Set<Class<?>> numberTypes = new HashSet<>(8);
        numberTypes.add(Byte.class);
        numberTypes.add(Short.class);
        numberTypes.add(Integer.class);
        numberTypes.add(Long.class);
        numberTypes.add(BigInteger.class);
        numberTypes.add(Float.class);
        numberTypes.add(Double.class);
        numberTypes.add(BigDecimal.class);

        STANDARD_NUMBER_TYPES = Collections.unmodifiableSet(numberTypes);
    }

    /**
     * 解析Number
     *
     * @param text        字符串
     * @param targetClass 目标类型
     * @param <T>         目标类型泛型
     * @return 解析后的Number
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T parseNumber(String text, Class<T> targetClass) {
        Assert.notNull(text, "Text must not be null");
        Assert.notNull(targetClass, "Target class must not be null");

        String trimmedText = trimAllWhitespace(text);
        if (Byte.class == targetClass) {
            return (T) (isHexNumber(trimmedText) ?
                    Byte.decode(trimmedText) :
                    Byte.valueOf(trimmedText));
        }

        if (Short.class == targetClass) {
            return (T) (isHexNumber(trimmedText) ?
                    Short.decode(trimmedText) :
                    Short.valueOf(trimmedText));
        }

        if (Integer.class == targetClass) {
            return (T) (isHexNumber(trimmedText) ?
                    Integer.decode(trimmedText) :
                    Integer.valueOf(trimmedText));
        }

        if (Long.class == targetClass) {
            return (T) (isHexNumber(trimmedText) ?
                    Long.decode(trimmedText) :
                    Long.valueOf(trimmedText));
        }

        if (BigInteger.class == targetClass) {
            return (T) (isHexNumber(trimmedText) ?
                    decodeBigInteger(trimmedText) :
                    new BigInteger(trimmedText));
        }

        if (Float.class == targetClass) {
            return (T) Float.valueOf(trimmedText);
        }

        if (Double.class == targetClass) {
            return (T) Double.valueOf(trimmedText);
        }

        if (BigDecimal.class == targetClass || Number.class == targetClass) {
            return (T) new BigDecimal(trimmedText);
        }

        throw new IllegalArgumentException("Cannot convert String [" + text
                + "] to target class [" + targetClass.getName() + "]");
    }

    /**
     * 去除字符串的所有空格
     * 包括收尾和中间
     *
     * @param str 字符串
     * @return 去除空格后的字符串
     */
    private static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }

        int len = str.length();
        StringBuilder stringBuilder = new StringBuilder(str.length());
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (!Character.isWhitespace(c)) {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 判断字符串是否有长度
     *
     * @param str 字符串
     * @return 是否有长度
     */
    private static boolean hasLength(@Nullable String str) {
        return str != null && !str.isEmpty();
    }

    /**
     * 判断字符串是否是十六进制数字
     *
     * @param str 字符串
     * @return 是否是十六进制数字
     */
    private static boolean isHexNumber(String str) {
        int index = str.startsWith("-") ? 1 : 0;
        return (str.startsWith("0x", index)
                || str.startsWith("0X", index)
                || str.startsWith("#", index));
    }

    /**
     * 解码BigInteger字符串
     *
     * @param str 字符串
     * @return 解码后的BigInteger
     */
    private static BigInteger decodeBigInteger(String str) {
        // 进制，默认10进制
        int radix = 10;
        // 索引
        int index = 0;
        // 是否是负数，默认否
        boolean negative = false;

        if (str.startsWith("-")) {
            // 负数
            negative = true;
            index++;
        }

        if (str.startsWith("0x", index) || str.startsWith("0X", index)) {
            // 16进制
            index += 2;
            radix = 16;
        } else if (str.startsWith("#", index)) {
            // 16进制
            index++;
            radix = 16;
        } else if (str.startsWith("0", index) && str.length() > 1 + index) {
            // 8进制
            index++;
            radix = 8;
        }

        BigInteger result = new BigInteger(str.substring(index), radix);

        return negative ? result.negate() : result;
    }
}
