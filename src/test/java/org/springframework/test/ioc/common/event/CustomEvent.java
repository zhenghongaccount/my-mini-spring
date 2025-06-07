package org.springframework.test.ioc.common.event;

import org.springframework.context.event.ApplicationContextEvent;

/**
 * @author zhenghong
 * @date 2025/6/7
 */
public class CustomEvent extends ApplicationContextEvent {

    /**
     * 创建一个新的 ApplicationEvent。
     *
     * @param source 事件源对象，表示事件最初发生的对象，不能为空
     * @throws IllegalArgumentException 如果 source 为 null，则抛出异常
     */
    public CustomEvent(Object source) {
        super(source);
    }
}