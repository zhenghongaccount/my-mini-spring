package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

/**
 * 由实现类表示希望获得所属的 {@link BeanFactory} 引用。
 *
 * <p>当一个 Bean 实现了此接口并被 Spring 容器管理时，
 * 容器会在 Bean 实例化之后、初始化之前调用 {@code setBeanFactory} 方法，
 * 将创建该 Bean 的 {@code BeanFactory} 注入进来。
 *
 * <p>这使得 Bean 可以在运行时访问容器本身，进行例如动态获取其他 Bean、
 * 查询 Bean 状态等操作。
 *
 * <p><strong>注意：</strong> 过度依赖该接口可能会导致代码与 Spring 框架耦合过重，
 * 一般推荐优先使用依赖注入（如 @Autowired）来注入所需依赖。
 *
 * <p>该接口是 Spring 中多种感知类接口之一，其父接口为 {@link Aware}。
 * <p>
 * 使用示例：
 * <pre>
 * {@code
 * @Component
 * public class MyBean implements BeanFactoryAware {
 *     private BeanFactory beanFactory;
 *
 *     @Override
 *     public void setBeanFactory(BeanFactory beanFactory) {
 *         this.beanFactory = beanFactory;
 *     }
 *
 *     public Object getAnotherBean(String name) {
 *         return beanFactory.getBean(name);
 *     }
 * }
 * }
 * </pre>
 *
 * @see Aware
 * @see BeanFactory
 * @see ApplicationContextAware
 *
 * @author zhenghong
 * @date 2025/6/5
 */
public interface BeanFactoryAware extends Aware{

    /**
     * 注入当前 Bean 所属的 {@link BeanFactory} 实例。
     *
     * @param beanFactory 管理当前 Bean 的 BeanFactory 实例
     * @throws BeansException 如果注入过程中出现错误
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
