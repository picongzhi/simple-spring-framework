package com.pcz.simple.spring.framework.core.convert.converter;

import cn.hutool.core.lang.Assert;

import java.util.Objects;
import java.util.Set;

/**
 * 通用的类型转换器
 *
 * @author picongzhi
 */
public interface GenericConverter {
    /**
     * 获取类型转换对
     *
     * @return 类型转换对
     */
    Set<ConvertiblePair> getConvertibleTypes();

    /**
     * 类型转换
     *
     * @param source     来源对象
     * @param sourceType 来源类型
     * @param targetType 目标类型
     * @return 目标对象
     */
    Object convert(Object source, Class<?> sourceType, Class<?> targetType);

    /**
     * 类型转换对
     */
    final class ConvertiblePair {
        /**
         * 来源类型
         */
        private final Class<?> sourceType;

        /**
         * 目标类型
         */
        private final Class<?> targetType;

        public ConvertiblePair(Class<?> sourceType, Class<?> targetType) {
            Assert.notNull(sourceType, "Source type must not be null");
            Assert.notNull(targetType, "Target type must not be null");
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        /**
         * 获取来源类型
         *
         * @return 来源类型
         */
        public Class<?> getSourceType() {
            return sourceType;
        }

        /**
         * 获取目标类型
         *
         * @return 目标类型
         */
        public Class<?> getTargetType() {
            return targetType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ConvertiblePair that = (ConvertiblePair) o;
            return Objects.equals(sourceType, that.sourceType) && Objects.equals(targetType, that.targetType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sourceType, targetType);
        }
    }
}
