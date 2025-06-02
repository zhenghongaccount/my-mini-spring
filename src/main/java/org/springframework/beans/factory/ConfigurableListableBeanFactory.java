package org.springframework.beans.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * ConfigurableListableBeanFactory 是一个“可配置 + 可列出”的高级容器接口，
 * 集成了 BeanFactory 的核心获取功能、ListableBeanFactory 的列举功能，
 * 并新增了获取 BeanDefinition 和提前实例化的能力。它服务于容器的完整初始化和配置阶段
 *
 * @author zhenghong
 * @date 2025/6/2
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory{

    /**
     * 根据名字查找BeanDefinition
     *
     * @param beanName beanName
     * @return beanDefinition
     * @throws BeansException 抛出BeansException
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 提前实例化所有单例实例
     *
     * @throws BeansException 抛出BeansException
     */
    void preInstantiateSingletons() throws BeansException;
}
