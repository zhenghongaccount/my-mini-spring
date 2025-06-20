package org.springframework.core.convert;

/**
 * 类型转换器注册接口，定义了用于注册各种类型转换器的方法。
 *
 * <p>该接口通常由 {@link org.springframework.core.convert.support.GenericConversionService}
 * 等转换服务实现，允许用户注册 {@link Converter}、{@link ConverterFactory} 或
 * {@link GenericConverter}，从而扩展系统的类型转换能力。
 *
 * <p>注册后的转换器将被用于执行类型转换，例如字符串转枚举、自定义对象转换等。
 *
 * @author zhenghong
 * @date 2025/6/19
 */
public interface ConverterRegistry {

    void addConverter(Converter<?, ?> converter);

    void addConverterFactory(ConverterFactory<?, ?> converterFactory);

    void addConverter(GenericConverter converter);

}
