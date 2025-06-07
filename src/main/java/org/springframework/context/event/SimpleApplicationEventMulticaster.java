package org.springframework.context.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 一个简单的事件广播器实现，继承自 {@link AbstractApplicationEventMulticaster}。
 * <p>
 * 它会遍历注册的 {@link ApplicationListener} 监听器，根据事件类型进行匹配，
 * 并调用支持该事件类型的监听器的 {@code onApplicationEvent} 方法。
 * </p>
 * 通常用于在 Spring 容器中广播事件。
 *
 * @author zhenghong
 * @date 2025/6/7
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener<?> listener : applicationListeners) {
            if (supportsEvent(listener, event)) {
                @SuppressWarnings("unchecked")
                ApplicationListener<ApplicationEvent> casted =
                        (ApplicationListener<ApplicationEvent>) listener;
                casted.onApplicationEvent(event);
            }
        }
    }

    /**
     * 判断给定监听器是否支持处理该事件类型。
     *
     * @param applicationListener 当前要判断的监听器
     * @param event               当前触发的事件
     * @return 如果该监听器的泛型参数与事件类型兼容，返回 true；否则返回 false
     */
    protected boolean supportsEvent(ApplicationListener<?> applicationListener, ApplicationEvent event) {
        Type type = applicationListener.getClass().getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();

        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }

        return eventClassName.isAssignableFrom(event.getClass());
    }
}
