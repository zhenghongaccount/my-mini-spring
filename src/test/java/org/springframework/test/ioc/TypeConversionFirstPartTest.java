package org.springframework.test.ioc;

import org.junit.jupiter.api.Test;
import org.springframework.core.convert.Converter;
import org.springframework.core.convert.support.StringToNumberConverterFactory;
import org.springframework.test.common.StringToBooleanConverter;
import org.springframework.test.common.StringToIntegerConverter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TypeConversionFirstPartTest {

    @Test
    public void testStringToIntegerConverter() throws Exception {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer num = converter.convert("8888");
        assertThat(num).isEqualTo(8888);
    }

    @Test
    public void testStringToNumberConverterFactory() throws Exception {
        StringToNumberConverterFactory converterFactory = new StringToNumberConverterFactory();

        Converter<String, Integer> stringToIntegerConverter = converterFactory.getConverter(Integer.class);
        Integer intNum = stringToIntegerConverter.convert("8888");
        assertThat(intNum).isEqualTo(8888);

        Converter<String, Long> stringToLongConverter = converterFactory.getConverter(Long.class);
        Long longNum = stringToLongConverter.convert("8888");
        assertThat(longNum).isEqualTo(8888L);
    }

    @Test
    public void testGenericConverter() throws Exception {
        StringToBooleanConverter converter = new StringToBooleanConverter();

        Boolean flag = (Boolean) converter.convert("true", String.class, Boolean.class);
        assertThat(flag).isTrue();
    }

}
