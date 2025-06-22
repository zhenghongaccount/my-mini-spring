package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

/**
 * {@code ObjectFactory} 是一个功能简单但功能强大的接口，
 * 用于提供对某个对象实例的延迟获取（lazy retrieval）或延迟创建（lazy initialization）。
 * <p>
 * 与直接持有对象实例不同，使用 {@code ObjectFactory} 可以将对象的创建或获取推迟到真正需要的时刻，
 * 常用于避免循环依赖、实现懒加载或作用域控制等场景。
 * <p>
 * 与 {@link FactoryBean} 不同，ObjectFactory 不是用来注册为 Spring Bean 的工厂类，
 * 而是作为框架内部或用户代码中获取对象的一种通用方式。
 *
 * @param <T> 要获取的对象类型
 * @see FactoryBean
 * @see org.springframework.beans.factory.config.ObjectFactoryCreatingFactoryBean
 * @see org.springframework.beans.factory.support.AbstractBeanFactory#getObjectForBeanInstance(Object, String)
 *
 * @author zhenghong
 * @date 2025/6/22
 */
public interface ObjectFactory<T> {

    /**
     * 返回该工厂管理的对象实例。每次调用可能返回相同或不同的实例，
     * 具体取决于工厂的实现策略（如单例、原型等）。
     *
     * @return 管理的对象实例
     * @throws BeansException 如果对象创建过程中出现错误
     */
    T getObject() throws BeansException;

}
