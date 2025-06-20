package org.springframework.core.convert;

/**
 * ConversionService 是一个通用的类型转换接口，主要用于在运行时将一个对象从一种类型转换为另一种类型。
 * 该接口广泛应用于 Spring 框架中，如数据绑定、属性配置、表达式语言处理等场景。
 * <p>
 * 常见用途包括：
 * - 表单参数到 JavaBean 属性的自动转换（如 String → Integer）
 * - 配置文件中的属性值类型转换
 * - 反序列化时的类型匹配
 * <p>
 * 使用方式通常是先调用 {@link #canConvert} 判断是否支持转换，再调用 {@link #convert} 执行转换。
 * <p>
 * Spring 提供的默认实现类为 {@code org.springframework.core.convert.support.DefaultConversionService}。
 *
 * @author zhenghong
 * @date 2025/6/19
 */
public interface ConversionService {

    boolean canConvert(Class<?> sourceType, Class<?> targetType);

    <T> T convert(Object source, Class<T> targetType);

}
