package org.springframework.core.convert;

/**
 * {@code ConverterFactory} 是一个工厂接口，用于创建多个将同一个源类型 {@code S}
 * 转换为不同目标子类型 {@code T} 的 {@link Converter} 实例。
 *
 * <p>它支持“多目标类型转换”，其中所有目标类型 {@code T} 都必须是通用父类型 {@code R} 的子类型。
 * 常用于批量构建如 {@code String -> Enum}、{@code String -> Number} 等多个目标类型的转换器。
 *
 * <p>例如，使用 {@code ConverterFactory<String, Enum>} 可以创建多个将字符串转换为不同枚举类的转换器。
 *
 * @param <S> 源类型（Source）：所有转换器输入的原始类型
 * @param <R> 目标类型上界（Result）：所有目标类型的父类或接口
 *
 * @see Converter
 * @see org.springframework.core.convert.ConversionService
 *
 * 示例：
 * <pre>
 *     ConverterFactory&lt;String, Enum&gt; factory = new StringToEnumConverterFactory();
 *     Converter&lt;String, Gender&gt; genderConverter = factory.getConverter(Gender.class);
 *     Gender gender = genderConverter.convert("MALE"); // Gender.MALE
 * </pre>
 *
 * @author zhenghong
 * @date 2025/6/19
 */
public interface ConverterFactory<S, R> {

    /**
     * 获取一个将源类型 {@code S} 转换为目标类型 {@code T} 的 {@link Converter}。
     *
     * @param <T> 目标类型，必须是 {@code R} 的子类型
     * @param targetType 目标类型的 Class 对象
     * @return 对应类型的转换器实例
     */
    <T extends  R> Converter<S, T> getConverter(Class<T> targetType);

}
