package org.springframework.context.event;

import org.springframework.context.ApplicationContext;

/**
 * {@code ContextClosedEvent} 是 Spring 框架中的一个容器关闭事件，
 * 表示 {@link ApplicationContext} 已经被关闭。
 * <p>
 * 当 Spring 应用上下文通过 {@code close()} 方法关闭时（例如在应用终止或手动调用关闭），
 * 会发布该事件，通知所有监听器进行资源清理等操作。
 * <p>
 * 应用程序可以通过实现 {@link org.springframework.context.ApplicationListener} 接口
 * 并监听 {@code ContextClosedEvent}，在上下文关闭时执行自定义逻辑。
 *
 * <pre>{@code
 * @Component
 * public class MyShutdownListener implements ApplicationListener<ContextClosedEvent> {
 *     @Override
 *     public void onApplicationEvent(ContextClosedEvent event) {
 *         // 执行资源释放、连接关闭等操作
 *     }
 * }
 * }</pre>
 *
 * @see org.springframework.context.ApplicationContext#close()
 * @see org.springframework.context.ApplicationListener
 * @see org.springframework.context.event.ContextRefreshedEvent
 * @see org.springframework.context.event.ApplicationContextEvent
 */
public class ContextClosedEvent extends ApplicationContextEvent {
    /**
     * 创建一个新的 ApplicationEvent。
     *
     * @param source 事件源对象，表示事件最初发生的对象，不能为空
     * @throws IllegalArgumentException 如果 source 为 null，则抛出异常
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
