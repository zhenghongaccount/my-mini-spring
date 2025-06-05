package org.springframework.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;

/**
 * 由实现类表示希望获得所属的 {@link ApplicationContext} 引用。
 *
 * <p>当一个 Bean 实现了此接口并被 Spring 容器管理时，
 * Spring 会在 Bean 初始化过程中自动调用 {@code setApplicationContext} 方法，
 * 将当前应用上下文 {@code ApplicationContext} 注入到该 Bean 中。
 *
 * <p>通过注入的 ApplicationContext，Bean 可以执行：
 * <ul>
 *   <li>访问容器中的其他 Bean（如 {@code getBean(...)})</li>
 *   <li>访问容器环境、资源加载器等</li>
 *   <li>发布自定义事件（如 {@code publishEvent(...)})</li>
 *   <li>读取配置属性、Profile 等上下文信息</li>
 * </ul>
 *
 * <p><strong>注意：</strong> 虽然通过该接口可以获取容器引用，
 * 但不建议在普通业务逻辑中过度使用，以避免代码与 Spring 容器强耦合。
 * 更推荐通过依赖注入的方式获取具体依赖。
 * <p>
 * 使用示例：
 * <pre>
 * {@code
 * @Component
 * public class MyService implements ApplicationContextAware {
 *     private ApplicationContext applicationContext;
 *
 *     @Override
 *     public void setApplicationContext(ApplicationContext applicationContext) {
 *         this.applicationContext = applicationContext;
 *     }
 *
 *     public void publishMyEvent(Object payload) {
 *         applicationContext.publishEvent(new MyCustomEvent(this, payload));
 *     }
 * }
 * }
 * </pre>
 *
 * @see Aware
 * @see ApplicationContext
 * @see org.springframework.context.event.ApplicationEventPublisher
 * @see org.springframework.beans.factory.BeanFactoryAware
 */
public interface ApplicationContextAware extends Aware {

    /**
     * 注入当前 Bean 所属的 {@link ApplicationContext} 实例。
     *
     * @param applicationContext 当前的 Spring 应用上下文
     * @throws BeansException 如果上下文注入失败
     */
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
