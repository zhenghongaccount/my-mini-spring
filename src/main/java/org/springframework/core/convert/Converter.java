package org.springframework.core.convert;

/**
 * 通用类型转换器接口，用于将一种类型的对象转换为另一种类型。
 *
 * <p>该接口通常用于注册到 {@link org.springframework.core.convert.ConversionService}
 * 中，作为自定义类型转换逻辑的一部分。例如，可用于将 String 类型转换为枚举、
 * 日期、布尔值、自定义对象等。
 *
 * @param <S> 源类型（Source）：要转换的原始类型
 * @param <T> 目标类型（Target）：要转换成的目标类型
 * <p>
 * 示例用法：
 * <pre>
 *     public class StringToIntegerConverter implements Converter&lt;String, Integer&gt; {
 *         public Integer convert(String source) {
 *             return Integer.valueOf(source);
 *         }
 *     }
 * </pre>
 *
 * 通常与 {@code ConversionService} 配合使用，实现更灵活的运行时类型转换机制。
 *
 * @author zhenghong
 * @date 2025/6/19
 */
public interface Converter<S, T> {

    T convert(S source);

}
