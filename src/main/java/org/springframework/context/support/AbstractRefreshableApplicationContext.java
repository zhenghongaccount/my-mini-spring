package org.springframework.context.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * AbstractRefreshableApplicationContext 是 Spring 容器中用于支持“可刷新”的应用上下文的抽象容器，
 * 它使用 DefaultListableBeanFactory 作为底层的 BeanFactory，并提供刷新（重建）的能力。
 * <p>
 * 子类通过实现 loadBeanDefinitions 方法来自定义 Bean 的加载方式（如 XML、注解等）。
 * <p>
 * 常见子类：ClassPathXmlApplicationContext、FileSystemXmlApplicationContext 等
 *
 * @author zhenghong
 * @date 2025/6/4
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

    // 当前上下文使用的 BeanFactory，负责容器中 Bean 的创建、管理和销毁
    DefaultListableBeanFactory beanFactory;

    /**
     * 刷新（重新创建） BeanFactory 的核心方法
     * 步骤包括：
     * 1. 创建一个新的 BeanFactory；
     * 2. 加载 Bean 定义（由子类实现具体加载逻辑）；
     * 注意：这里创建的 beanFactory 还未赋值给成员变量，需要子类手动赋值或扩展。
     */
    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    /**
     * 创建一个新的 DefaultListableBeanFactory 实例。
     * 这是 Spring 默认使用的功能最强的 BeanFactory 实现类。
     */
    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    /**
     * 抽象方法：由子类实现具体的 Bean 定义加载逻辑。
     * 通常包括从 XML、注解、Groovy 脚本等方式读取 Bean 配置。
     *
     * @param beanFactory 目标 BeanFactory，子类负责将配置加载进该工厂
     */
    abstract protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException;

    /**
     * 获取当前上下文中的 BeanFactory。
     * 外部可以通过该方法访问和操作 BeanFactory。
     */
    @Override
    public DefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

}
