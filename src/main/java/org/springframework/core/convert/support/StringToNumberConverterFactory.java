package org.springframework.core.convert.support;

import org.springframework.core.convert.Converter;
import org.springframework.core.convert.ConverterFactory;

/**
 * {@code StringToNumberConverterFactory} 是 Spring 类型转换框架中的一个标准实现，
 * 用于将 {@link String} 类型转换为任意 {@link Number} 子类型（如 {@link Integer}、{@link Long} 等）。
 *
 * <p>它实现了 {@link ConverterFactory} 接口，能够根据目标类型动态生成对应的 {@link Converter}。
 *
 * <p>该工厂在 Spring 容器初始化时会被默认注册到 {@code DefaultConversionService} 中，
 * 用于支持属性注入、配置绑定等场景中的自动类型转换。
 *
 * @author zhenghong
 * @since 2025/6/20
 */
public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {

    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToNumber<T> (targetType);
    }

    private static class StringToNumber<T extends Number> implements Converter<String, T> {

        private final Class<?> targetType;

        public StringToNumber(Class<?> targetType) {
            this.targetType = targetType;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T convert(String source) {
            if (source.isEmpty()) {
                return null;
            }
            if (targetType.equals(Integer.class)) {
                return (T) Integer.valueOf(source);
            } else if (targetType.equals(Long.class)) {
                return (T) Long.valueOf(source);
            }
            // TODO 其他类型转换
            else {
                throw new IllegalArgumentException(
                        "Cannot convert String [" + source + "] to target class [" + targetType.getName() + "]"
                );
            }
        }
    }
}
