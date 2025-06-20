package org.springframework.test.common;

import org.springframework.core.convert.Converter;

/**
 * @author zhenghong
 * @date 2025/6/20
 */
public class StringToIntegerConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String source) {
        return Integer.valueOf(source);
    }
}
