package org.springframework.context.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * {@link BeanPostProcessor} 的一个实现类，用于在 Bean 初始化之前，
 * 回调所有实现了 {@link ApplicationContextAware} 接口的 Bean，
 * 注入当前的 {@link ApplicationContext}。
 *
 * <p>这是 Spring 容器支持感知型 Bean 的核心机制之一。
 * 当某个 Bean 实现了 {@code ApplicationContextAware} 接口时，
 * 容器会在初始化之前通过本处理器调用其 {@code setApplicationContext(...)} 方法，
 * 将当前上下文注入给该 Bean。
 *
 * <p>本处理器通常由 Spring 容器自动注册，不需要用户显式配置。
 * 它确保实现 {@link Aware} 子接口的 Bean 能感知并操作容器环境。
 *
 * <p>可以扩展此处理器以支持更多 Aware 接口（如 {@code EnvironmentAware}、
 * {@code ResourceLoaderAware} 等）。
 * <p>
 * 使用示例：
 * <pre>
 * {@code
 * @Component
 * public class MyBean implements ApplicationContextAware {
 *     private ApplicationContext context;
 *
 *     @Override
 *     public void setApplicationContext(ApplicationContext applicationContext) {
 *         this.context = applicationContext;
 *     }
 * }
 * }
 * </pre>
 *
 * @see ApplicationContextAware
 * @see BeanPostProcessor
 * @see org.springframework.beans.factory.BeanFactoryAware
 * @see org.springframework.context.EnvironmentAware
 *
 * @author zhenghong
 * @date 2025/6/5
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
