package org.springframework.beans.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowiredCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * ConfigurableListableBeanFactory 是一个“可配置 + 可列出”的高级容器接口，
 * 集成了 BeanFactory 的核心获取功能、ListableBeanFactory 的列举功能，
 * 并新增了获取 BeanDefinition 和提前实例化的能力。它服务于容器的完整初始化和配置阶段
 *
 * @author zhenghong
 * @date 2025/6/2
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowiredCapableBeanFactory, ConfigurableBeanFactory {

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

    /**
     * 向容器中添加一个 BeanPostProcessor。
     * BeanPostProcessor 用于在 Bean 初始化前后执行自定义逻辑，由用户自行实现
     * 是实现 AOP、依赖注入等逻辑的重要扩展点
     *
     * @param beanPostProcessor 要添加的 BeanPostProcessor 实例
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
