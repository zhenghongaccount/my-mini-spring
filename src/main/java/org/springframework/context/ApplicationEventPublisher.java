package org.springframework.context;

/**
 * ApplicationEventPublisher 是 Spring 框架中的事件发布接口。
 * <p>
 * 它用于将 {@link org.springframework.context.ApplicationEvent} 事件发布到
 * Spring 的应用上下文中，进而被相应的事件监听器捕获并处理。
 * <p>
 * 常用于服务层或控制器中触发领域事件、业务事件等。
 * <p>
 * 使用方式：Spring 会自动将该接口的实现注入到 Bean 中，直接调用 {@code publishEvent()} 方法即可。
 * <p>
 * 示例用法：
 * <pre>{@code
 * @Component
 * public class UserService {
 *
 *     @Autowired
 *     private ApplicationEventPublisher publisher;
 *
 *     public void createUser(String name) {
 *         // 创建用户逻辑...
 *         publisher.publishEvent(new UserCreatedEvent(this, name));
 *     }
 * }
 * }</pre>
 */
public interface ApplicationEventPublisher {

    /**
     * 发布一个 Spring 应用事件。
     * <p>
     * 事件对象必须是 {@link ApplicationEvent} 或其子类的实例，
     * 通常由自定义事件类扩展而来。
     * <p>
     * 所有已注册并监听该事件类型的监听器都将被调用。
     *
     * @param event 要发布的事件对象，不能为 null
     */
    void publishEvent(ApplicationEvent event);

}
