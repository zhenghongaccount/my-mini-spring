package org.springframework.beans.factory.config;

import org.springframework.beans.factory.HierarchicalBeanFactory;

/**
 * ConfigurableBeanFactory 是 Spring 容器中用于配置 BeanFactory 行为的接口，
 * 提供了对 Bean 生命周期管理的扩展功能，比如添加 BeanPostProcessor、
 * 自定义作用域、注册单例等
 * <p>
 * 它继承自 BeanFactory（用于获取和管理 Bean）和 SingletonBeanRegistry（用于注册和获取单例对象），
 * 是实现可配置、可扩展容器行为的关键接口
 *
 * @author zhenghong
 * @date 2025/6/3
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory,SingletonBeanRegistry {

    /**
     * 向容器中添加一个 BeanPostProcessor。
     * BeanPostProcessor 用于在 Bean 初始化前后执行自定义逻辑，由用户自行实现
     * 是实现 AOP、依赖注入等逻辑的重要扩展点
     *
     * @param beanPostProcessor 要添加的 BeanPostProcessor 实例
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}
