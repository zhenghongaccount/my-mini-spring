package org.springframework.context;

import java.util.EventObject;

/**
 * ApplicationEvent 是 Spring 框架中所有应用事件的基类。
 * <p>
 * 它继承自 Java 标准的事件类 {@link java.util.EventObject}，
 * 代表 Spring 应用上下文中的一个事件，通常用于事件发布与监听机制中。
 * <p>
 * 自定义事件类应继承自此类，并通过 {@link org.springframework.context.ApplicationEventPublisher} 发布，
 * 然后通过 {@link org.springframework.context.event.EventListener} 等方式进行监听。
 *
 * @author zhenghong
 * @date 2025/6/5
 */
public abstract class ApplicationEvent extends EventObject {

    /**
     * 创建一个新的 ApplicationEvent。
     *
     * @param source 事件源对象，表示事件最初发生的对象，不能为空
     * @throws IllegalArgumentException 如果 source 为 null，则抛出异常
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
