package org.springframework.context;

import java.util.EventListener;

/**
 * ApplicationListener 是 Spring 的通用事件监听器接口。
 * <p>
 * 任何实现该接口的 Bean 都可以监听指定类型的 {@link ApplicationEvent} 事件。
 * 当事件发布时（通过 {@link org.springframework.context.ApplicationEventPublisher}），
 * 所有匹配类型的监听器都会被调用 {@code onApplicationEvent(E event)} 方法。
 *
 * <p>你可以通过实现该接口，监听特定类型的业务事件或框架事件，如：
 * ContextRefreshedEvent、UserRegisteredEvent 等。
 * <p>
 * 示例用法：
 * <pre>{@code
 * @Component
 * public class MyEventListener implements ApplicationListener<UserRegisteredEvent> {
 *     @Override
 *     public void onApplicationEvent(UserRegisteredEvent event) {
 *         System.out.println("监听到用户注册事件：" + event.getUsername());
 *     }
 * }
 * }</pre>
 *
 * @param <E> 监听的事件类型，必须是 ApplicationEvent 或其子类
 *
 * @author zhenghong
 * @date 2025/6/5
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    /**
     * 事件触发时调用的方法。
     * <p>
     * 当一个 {@link ApplicationEvent} 或其子类事件发布后，
     * 所有监听该类型事件的 ApplicationListener 实例都会调用此方法。
     *
     * @param event 发布的事件对象，类型由泛型 E 决定
     */
    void onApplicationEvent(E event);
    
}
