package org.springframework.beans.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.StringValueResolver;

import java.io.InputStream;
import java.util.Properties;

/**
 * 属性占位符配置器，用于处理Spring Bean定义中的属性占位符（${...}）。
 * <p>
 * 本类实现了{@link BeanFactoryPostProcessor}接口，在Spring容器初始化阶段，
 * 将Bean定义中的属性占位符替换为实际配置值。
 *
 * <p><b>典型用法：</b>
 * <pre class="code">
 * &lt;bean class="org.springframework.core.io.resource.PropertyPlaceholderConfigurer"&gt;
 *   &lt;property name="location" value="classpath:config.properties"/&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <p>配置文件示例（config.properties）：
 * <pre class="code">
 * jdbc.url=jdbc:mysql://localhost:3306/mydb
 * jdbc.username=root
 * </pre>
 *
 * <p>在Bean定义中使用占位符：
 * <pre class="code">
 * &lt;bean id="dataSource" class="com.zaxxer.hikari.HikariDataSource"&gt;
 *   &lt;property name="jdbcUrl" value="${jdbc.url}"/&gt;
 *   &lt;property name="username" value="${jdbc.username}"/&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * @author spring-framework team
 * @see BeanFactoryPostProcessor
 * @see org.springframework.beans.factory.config.PropertyResourceConfigurer
 *
 * @author zhenghong
 * @date 2025/6/13
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    private static final String PLACEHOLDER_PREFIX = "${";

    private static final String PLACEHOLDER_SUFFIX = "}";

    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Properties properties = loadProperties();
        processProperties(beanFactory, properties);
        PlaceholderResolvingStringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
        beanFactory.addEmbeddedValueResolver(valueResolver);
    }

    /**
     * 加载属性配置文件
     *
     * @return Properties
     */
    private Properties loadProperties() {
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            InputStream inputStream = resource.getInputStream();
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (Exception e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    /**
     * 处理所有Bean定义中的属性值，替换其中的占位符。
     *
     * @param beanFactory 可配置的可列表化Bean工厂
     * @param properties 已加载的属性集合
     * @throws BeansException 如果处理过程中发生错误
     */
    private void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            resolvePropertiesValues(beanDefinition, properties);
        }
    }

    /**
     * 解析单个Bean定义中的属性值，替换其中的占位符。
     *
     * @param beanDefinition Bean定义对象
     * @param properties 已加载的属性集合
     */
    private void resolvePropertiesValues(BeanDefinition beanDefinition, Properties properties) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            Object value = propertyValue.getValue();
            if (value instanceof String) {
                value = resolvePlaceHolder(value.toString(), properties);
                propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), value));
            }
        }
    }

    private String resolvePlaceHolder(String value, Properties properties) {
        //TODO 仅简单支持一个占位符的格式
        String strVal = value;
        StringBuffer buf = new StringBuffer(strVal);
        int startIndex = strVal.indexOf(PLACEHOLDER_PREFIX);
        int endIndex = strVal.indexOf(PLACEHOLDER_SUFFIX);
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            String propKey = strVal.substring(startIndex + 2, endIndex);
            String propVal = properties.getProperty(propKey);
            buf.replace(startIndex, endIndex + 1, propVal);
        }
        return buf.toString();
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String resolveStringValue(String strVal) {
            return PropertyPlaceholderConfigurer.this.resolvePlaceHolder(strVal, properties);
        }

    }
}
