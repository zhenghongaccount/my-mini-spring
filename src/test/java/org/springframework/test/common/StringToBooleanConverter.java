package org.springframework.test.common;

import org.junit.jupiter.api.Test;
import org.springframework.core.convert.GenericConverter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.core.convert.support.StringToNumberConverterFactory;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author zhenghong
 * @date 2025/6/20
 */
public class StringToBooleanConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, Boolean.class));
    }

    @Override
    public Object convert(Object source, Class<?> sourceType, Class<?> targetType) {
        return Boolean.valueOf((String) source);
    }

    @Test
    public void testGenericConversionService() throws Exception {
        GenericConversionService conversionService = new GenericConversionService();
        conversionService.addConverter(new StringToIntegerConverter());

        Integer intNum = conversionService.convert("8888", Integer.class);
        assertThat(conversionService.canConvert(String.class, Integer.class)).isTrue();
        assertThat(intNum).isEqualTo(8888);

        conversionService.addConverterFactory(new StringToNumberConverterFactory());
        assertThat(conversionService.canConvert(String.class, Long.class)).isTrue();
        Long longNum = conversionService.convert("8888", Long.class);
        assertThat(longNum).isEqualTo(8888L);

        conversionService.addConverter(new StringToBooleanConverter());
        assertThat(conversionService.canConvert(String.class, Boolean.class)).isTrue();
        Boolean flag = conversionService.convert("true", Boolean.class);
        assertThat(flag).isTrue();
    }
}
