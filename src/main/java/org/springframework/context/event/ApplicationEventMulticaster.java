package org.springframework.context.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * {@code ApplicationMulticaster} 是一个事件广播器接口，
 * 定义了向多个 {@link ApplicationListener} 监听器广播 {@link ApplicationEvent} 事件的基本操作。
 * <p>
 * 它是 Spring 事件发布机制的核心组件之一，常由 Spring 容器中的 {@code ApplicationEventMulticaster} 实现。
 * <p>
 * 使用场景包括发布自定义事件、监听容器生命周期事件、模块解耦等。
 *
 * @see ApplicationListener
 * @see ApplicationEvent
 *
 * @author zhenghong
 * @date 2025/6/7
 */
public interface ApplicationEventMulticaster {

    /**
     * 注册一个事件监听器，用于接收广播的事件。
     *
     * @param listener 要注册的事件监听器，不应为 {@code null}
     */
    void addApplicationListener(final ApplicationListener<?> listener);

    /**
     * 移除一个已注册的事件监听器，取消其事件接收。
     *
     * @param listener 要移除的监听器
     */
    void removeApplicationListener(final ApplicationListener<?> listener);

    /**
     * 将给定的事件广播给所有已注册的监听器。
     * <p>
     * 所有匹配事件类型的监听器都会接收到该事件。
     *
     * @param event 要广播的事件对象，不能为空
     */
    void multicastEvent(final ApplicationEvent event);
}
