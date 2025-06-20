package org.springframework.core.convert.support;

import org.springframework.core.convert.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * {@code GenericConversionService} 是 Spring 框架中通用的类型转换服务实现，
 * 同时实现了 {@link ConversionService} 和 {@link ConverterRegistry} 接口。
 *
 * <p>它允许用户动态注册 {@link Converter}、{@link ConverterFactory} 以及 {@link GenericConverter}，
 * 并支持在运行时进行灵活的类型转换。
 *
 * <p>在 Spring 中，常用于注册到 {@code DefaultConversionService} 或自定义配置中，
 * 作为核心的类型转换引擎。
 *
 * <p>支持类型继承结构匹配，例如 {@code Integer → Number}, {@code String → Object} 等。
 *
 * @author zhenghong
 * @date 2025/6/20
 */
public class GenericConversionService implements ConversionService, ConverterRegistry {

    private final Map<GenericConverter.ConvertiblePair, GenericConverter> converters = new HashMap<>();

    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        GenericConverter converter = getConverter(sourceType, targetType);
        return converter != null;
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        Class<?> sourceType = source.getClass();
        GenericConverter converter = getConverter(sourceType, targetType);
        Object result = converter.convert(source, sourceType, targetType);
        if (!targetType.isInstance(result)) {
            throw new ClassCastException("Cannot cast " + result + " to " + targetType);
        }
        return targetType.cast(result);
    }

    @Override
    public void addConverter(Converter<?, ?> converter) {
        GenericConverter.ConvertiblePair requiredTypeInfo = getRequiredTypeInfo(converter);
        ConverterAdapter converterAdapter = new ConverterAdapter(requiredTypeInfo, converter);
        Set<GenericConverter.ConvertiblePair> convertibleTypes = converterAdapter.getConvertibleTypes();
        for (GenericConverter.ConvertiblePair pair : convertibleTypes) {
            converters.put(pair, converterAdapter);
        }
    }

    @Override
    public void addConverterFactory(ConverterFactory<?, ?> converterFactory) {
        GenericConverter.ConvertiblePair typeInfo = getRequiredTypeInfo(converterFactory);
        ConverterFactoryAdapter converterFactoryAdapter = new ConverterFactoryAdapter(converterFactory, typeInfo);
        Set<GenericConverter.ConvertiblePair> convertibleTypes = converterFactoryAdapter.getConvertibleTypes();
        for (GenericConverter.ConvertiblePair convertibleType : convertibleTypes) {
            converters.put(convertibleType, converterFactoryAdapter);
        }
    }

    @Override
    public void addConverter(GenericConverter converter) {
        Set<GenericConverter.ConvertiblePair> convertibleTypes = converter.getConvertibleTypes();
        for (GenericConverter.ConvertiblePair convertibleType : convertibleTypes) {
            converters.put(convertibleType, converter);
        }
    }

    private List<Class<?>> getClassHierarchy(Class<?> clazz) {
        List<Class<?>> hierarchy = new ArrayList<Class<?>>();
        while (clazz != null) {
            hierarchy.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return hierarchy;
    }

    protected GenericConverter getConverter(Class<?> sourceType, Class<?> targetType) {
        List<Class<?>> sourceCandidates = getClassHierarchy(sourceType);
        List<Class<?>> targetCandidates = getClassHierarchy(targetType);
        for (Class<?> sourceCandidate : sourceCandidates) {
            for (Class<?> targetCandidate : targetCandidates) {
                GenericConverter.ConvertiblePair convertiblePair = new GenericConverter.ConvertiblePair(sourceCandidate, targetCandidate);
                GenericConverter converter = converters.get(convertiblePair);
                if (converter != null) {
                    return converter;
                }
            }
        }
        return null;
    }

    private GenericConverter.ConvertiblePair getRequiredTypeInfo(Object object) {
        Type[] types = object.getClass().getGenericInterfaces();
        ParameterizedType parameterized = (ParameterizedType) types[0];
        Type[] actualTypeArguments = parameterized.getActualTypeArguments();
        Class<?> sourceType = (Class<?>) actualTypeArguments[0];
        Class<?> targetType = (Class<?>) actualTypeArguments[1];
        return new GenericConverter.ConvertiblePair(sourceType, targetType);
    }

    private static class ConverterAdapter implements GenericConverter {

        private final ConvertiblePair typeInfo;

        private final Converter<Object, Object> converter;

        @SuppressWarnings("unchecked")
        public ConverterAdapter(ConvertiblePair typeInfo, Converter<?, ?> converter) {
            this.typeInfo = typeInfo;
            this.converter = (Converter<Object, Object>) converter;
        }

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(typeInfo);
        }

        @Override
        public Object convert(Object source, Class<?> sourceType, Class<?> targetType) {
            return converter.convert(source);
        }
    }

    private static class ConverterFactoryAdapter implements GenericConverter {

        private final ConverterFactory<Object, Object> converterFactory;

        private final ConvertiblePair typeInfo;

        @SuppressWarnings("unchecked")
        public ConverterFactoryAdapter(ConverterFactory<?, ?> converterFactory, ConvertiblePair typeInfo) {
            this.converterFactory = (ConverterFactory<Object, Object>) converterFactory;
            this.typeInfo = typeInfo;
        }

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(typeInfo);
        }

        @Override
        public Object convert(Object source, Class<?> sourceType, Class<?> targetType) {
            return converterFactory.getConverter(targetType).convert(source);
        }
    }
}
