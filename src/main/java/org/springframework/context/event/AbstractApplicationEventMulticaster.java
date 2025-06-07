package org.springframework.context.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationListener;

import java.util.HashSet;
import java.util.Set;

/**
 * 抽象的事件广播器基类，实现了 {@link ApplicationEventMulticaster} 和 {@link BeanFactoryAware} 接口，
 * 提供了监听器管理的基础功能和对 Spring {@link BeanFactory} 的感知能力。
 *
 * <p>该类维护了一个线程不安全的监听器集合，用于存储所有注册的 {@link ApplicationListener}。
 * 子类需要实现具体的事件广播逻辑，例如遍历监听器并调用其事件处理方法。
 *
 * <p>实现了 {@link BeanFactoryAware} 接口，允许注入 Spring 容器中的 {@link BeanFactory}，
 * 方便子类获取 Bean 或延迟加载监听器。
 *
 * <p>通常，Spring 内部会使用 {@link org.springframework.context.event.SimpleApplicationEventMulticaster} 作为此类的具体实现。
 *
 * @see ApplicationEventMulticaster
 * @see BeanFactoryAware
 * @see org.springframework.context.event.SimpleApplicationEventMulticaster
 *
 * @author zhenghong
 * @date 2025/6/7
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    /**
     * 保存所有注册的事件监听器的集合。
     * 注意：该集合不是线程安全的，具体实现中可能需要进行同步处理。
     */
    protected final Set<ApplicationListener<?>> applicationListeners = new HashSet<>();

    /**
     * Spring 容器的 BeanFactory，子类可以通过它访问和管理 Bean。
     */
    private BeanFactory beanFactory;

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add(listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
