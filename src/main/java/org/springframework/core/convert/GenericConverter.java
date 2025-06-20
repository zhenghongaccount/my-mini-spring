package org.springframework.core.convert;

import java.util.Set;

/**
 * 通用类型转换器接口，支持多个源类型和目标类型之间的复杂转换。
 *
 * <p>该接口允许一个转换器声明它支持的多个类型对（sourceType -> targetType），
 * 并在转换时提供源对象、源类型和目标类型，实现灵活的转换逻辑。
 *
 * <p>通常用于框架内部，支持更加复杂和动态的转换场景，比如同时支持
 * String 转 Integer、String 转 Boolean 等多种类型转换。
 *
 * @author zhenghong
 * @date 2025/6/19
 */
public interface GenericConverter {

    Set<ConvertiblePair> getConvertibleTypes();

    Object convert(Object source, Class<?> sourceType, Class<?> targetType);

    /**
     * 表示一个源类型到目标类型的转换对，用于声明转换器支持的类型组合。
     */
     class ConvertiblePair {
        private final Class<?> sourceType;

        private final Class<?> targetType;

        public ConvertiblePair(Class<?> sourceType, Class<?> targetType) {
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        public Class<?> getSourceType() {
            return this.sourceType;
        }

        public Class<?> getTargetType() {
            return this.targetType;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (obj.getClass() != ConvertiblePair.class) {
                return false;
            }

            ConvertiblePair other = (ConvertiblePair) obj;
            return this.sourceType.equals(other.getSourceType()) && this.targetType.equals(other.getTargetType());
        }

        @Override
        public int hashCode() {
            return this.sourceType.hashCode() * 31 + this.targetType.hashCode();
        }
    }
}
