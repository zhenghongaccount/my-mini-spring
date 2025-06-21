package org.springframework.test.common;

import org.springframework.core.convert.GenericConverter;

import java.util.Collections;
import java.util.Set;

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
}
