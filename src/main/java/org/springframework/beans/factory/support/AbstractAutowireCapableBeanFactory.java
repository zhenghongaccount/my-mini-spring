package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 创建类的工厂，BeanFactory的实现类
 *
 * @author zhenghong
 * @date 2025/5/31
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    public InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String name, BeanDefinition beanDefinition) throws BeansException {
        return doCreateBean(name, beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) throws BeansException{
        Class<?> beanClass = beanDefinition.getBeanClass();
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition);
            //为bean设置属性值
            applyPropertyValues(beanName,bean,beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        addSingleTon(beanName, bean);
        return bean;
    }

    /**
     * 为bean填充属性
     *
     * @param bean bean
     * @param beanDefinition bean信息
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            Class<?> beanClass = beanDefinition.getBeanClass();

            for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                Class<?> type = beanClass.getDeclaredField(name).getType();
                String methodName = "set" + name.substring(0,1).toUpperCase() + name.substring(1);
                Method method = beanClass.getDeclaredMethod(methodName, type);
                method.invoke(bean,value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values for bean: " + beanName, e);
        }
    }

    public Object createBeanInstance(BeanDefinition beanDefinition) {
        return getInstantiationStrategy().instantiate(beanDefinition);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
