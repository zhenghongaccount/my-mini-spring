package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * BeanDefinition注册表接口
 *
 * @author zhenghong
 * @date 2025/5/31
 */
public interface BeanDefinitionRegistry {

    /**
     * 向类注册表中注册类的信息
     *
     * @param beanName 对象名
     * @param beanDefinition 类的描述
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 根据名字查找BeanDefinition
     *
     * @param beanName beanName
     * @return beanDefinition
     * @throws BeansException 抛出BeansException
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 是否包含指定名称的beanDefinition
     *
     * @param beanName beanName
     * @return boolean
     */
    boolean containsBeanDefinition(String beanName);
}
