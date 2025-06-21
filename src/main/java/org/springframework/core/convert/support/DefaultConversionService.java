package org.springframework.core.convert.support;

import org.springframework.core.convert.ConverterRegistry;

/**
 * 默认的类型转换服务实现，扩展自 {@link GenericConversionService}。
 * <p>
 * 它在构造时自动注册一组常用的默认类型转换器（如字符串转数字等），
 * 是 Spring 容器中常用的 {@link org.springframework.core.convert.ConversionService} 实现。
 * <p>
 * 用户可在此基础上继续注册自定义的转换器。
 *
 * @see GenericConversionService
 * @see org.springframework.core.convert.ConversionService
 * @see org.springframework.core.convert.converter.Converter
 * @see org.springframework.core.convert.converter.ConverterFactory
 * @see org.springframework.core.convert.converter.GenericConverter
 *
 * @author zhenghong
 * @date 2025/6/20
 */
public class DefaultConversionService extends GenericConversionService{

    public DefaultConversionService() {
        addDefaultConverters(this);
    }

    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
        // TODO 添加其他 ConverterFactory
    }
}
