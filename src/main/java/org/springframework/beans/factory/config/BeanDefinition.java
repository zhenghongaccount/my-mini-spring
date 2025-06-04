package org.springframework.beans.factory.config;

import org.springframework.beans.PropertyValues;

/**
 * BeanDefinition 实例保存 bean 的信息，包括 class 类型、方法构造参数、是否为单例等，此处简化只包含 class 类型
 *
 * @author zhenghong
 * @date 2025/5/31
 */
public class BeanDefinition {

    private Class<?> BeanClass;

    private PropertyValues propertyValues;

    private String initMethodName;

    private String destroyMethodName;

    public BeanDefinition(Class<?> beanClass) {
        this(beanClass,null);
    }

    public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues) {
        BeanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    public Class<?> getBeanClass() {
        return BeanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        BeanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }


}
