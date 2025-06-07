package org.springframework.context.event;

import org.springframework.context.ApplicationContext;

/**
 * {@code ContextRefreshedEvent} 表示 Spring {@link ApplicationContext} 刷新完成后的事件。
 * <p>
 * 当应用上下文启动或调用 {@code refresh()} 方法完成初始化时，会发布该事件，
 * 通知所有监听器容器已准备就绪，所有单例 Bean 已经被初始化。
 * <p>
 * 监听此事件可以执行启动后需要进行的初始化逻辑，例如预加载数据、启动任务等。
 *
 * <pre>{@code
 * @Component
 * public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
 *     @Override
 *     public void onApplicationEvent(ContextRefreshedEvent event) {
 *         System.out.println("容器刷新完成，开始执行初始化逻辑");
 *     }
 * }
 * }</pre>
 *
 * @see ApplicationContextEvent
 * @see org.springframework.context.ApplicationListener
 * @see org.springframework.context.ConfigurableApplicationContext#refresh()
 *
 * @author zhenghong
 * @date 2025/6/7
 */
public class ContextRefreshedEvent extends ApplicationContextEvent {
    /**
     * 创建一个新的 ApplicationEvent。
     *
     * @param source 事件源对象，表示事件最初发生的对象，不能为空
     * @throws IllegalArgumentException 如果 source 为 null，则抛出异常
     */
    public ContextRefreshedEvent(ApplicationContext source) {
        super(source);
    }
}
