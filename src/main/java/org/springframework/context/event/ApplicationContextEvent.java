package org.springframework.context.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

/**
 * {@code ApplicationContextEvent} 是所有与 {@link ApplicationContext} 相关的
 * Spring 框架事件的抽象基类。
 * <p>
 * 该类的子类用于表示 Spring 应用上下文的生命周期事件，例如：
 * {@code ContextRefreshedEvent}, {@code ContextStartedEvent},
 * {@code ContextStoppedEvent}, 和 {@code ContextClosedEvent}。
 * <p>
 * 每个 {@code ApplicationContextEvent} 的事件源（source）必须是一个 {@link ApplicationContext} 实例。
 *
 * @author —
 * @see org.springframework.context.ApplicationContext
 * @see org.springframework.context.ApplicationEvent
 * @see org.springframework.context.event.ContextRefreshedEvent
 *
 * @author zhenghong
 * @date 2025/6/7
 */
public abstract class ApplicationContextEvent extends ApplicationEvent {
    /**
     * 创建一个新的 ApplicationEvent。
     *
     * @param source 事件源对象，表示事件最初发生的对象，不能为空
     * @throws IllegalArgumentException 如果 source 为 null，则抛出异常
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * 获取发布该事件的 {@link ApplicationContext}。
     *
     * @return 发布该事件的应用上下文
     */
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
