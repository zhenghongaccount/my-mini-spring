package org.springframework.beans.factory.config;

/**
 * BeanDefinition实例保存bean的信息，包括class类型、方法构造参数、是否为单例等，此处简化只包含class类型
 */
public class BeanDefinition {
    private Class<?> BeanClass;

    public BeanDefinition(Class<?> beanClass) {
        BeanClass = beanClass;
    }

    public Class<?> getBeanClass() {
        return BeanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        BeanClass = beanClass;
    }
}
