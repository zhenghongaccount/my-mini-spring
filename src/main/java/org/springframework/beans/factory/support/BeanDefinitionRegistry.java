package org.springframework.beans.factory.support;

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
     * @param beanName 对象名
     * @param beanDefinition 类的描述
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
