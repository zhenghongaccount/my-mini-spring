package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

/**
 * Bean 容器的顶层接口，定义了获取 Bean 实例的基本功能。
 * <p>
 * 所有的 IoC 容器都需要实现该接口，提供按名称或类型获取 Bean 的能力。
 * 这是 Spring 容器的核心接口之一，也是后续更复杂接口（如 ApplicationContext）的基础。
 *
 * @author zhenghong
 * @date 2025/6/3
 */
public interface BeanFactory {

    /**
     * 根据 Bean 的名称获取对应的 Bean 实例。
     *
     * @param name Bean 的名称（ID 或别名）
     * @return 对应的 Bean 实例
     * @throws BeansException 如果找不到对应的 Bean，或者创建失败
     */
    Object getBean(String name) throws BeansException;

    /**
     * 根据 Bean 的名称和类型获取 Bean 实例，带类型安全检查。
     *
     * @param name Bean 的名称
     * @param requiredType 期望返回的 Bean 类型
     * @param <T> 泛型参数，表示返回值的类型
     * @return 指定类型的 Bean 实例
     * @throws BeansException 如果 Bean 不存在或类型不匹配
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    /**
     * 根据所需类型从容器中获取唯一的 Bean 实例。
     * <p>
     * 如果容器中存在多个该类型的 Bean，将抛出 {@link BeansException} 异常；
     * 如果不存在任何匹配的 Bean，也会抛出异常。
     *
     * @param requiredType 要获取的 Bean 的类型
     * @param <T> 返回的 Bean 类型
     * @return 与指定类型匹配的唯一 Bean 实例
     * @throws BeansException 如果匹配不到或匹配多个 Bean 时抛出
     *
     * @author zhenghong
     * @date 2015/6/15
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;
}
