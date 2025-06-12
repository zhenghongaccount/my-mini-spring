package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;

/**
 * {@code InstantiationAwareBeanPostProcessor} 是 {@link org.springframework.beans.factory.config.BeanPostProcessor} 的一个扩展接口，
 * 提供了在 Spring 容器实例化 Bean（即调用构造器）之前的回调机制。
 * <p>
 * 实现此接口可以在 Bean 被实际创建之前进行拦截，用于实现例如动态代理、提前替代实例、
 * 或者其他定制化实例化逻辑。
 * <p>
 * 典型用例包括：
 * <ul>
 *     <li>创建 AOP 代理对象（如 AspectJ 自动代理）</li>
 *     <li>跳过默认构造流程并直接返回自定义 Bean 实例</li>
 *     <li>条件性地禁止某些 Bean 被实例化</li>
 * </ul>
 *
 * @author Spring
 * @see org.springframework.beans.factory.config.BeanPostProcessor
 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory
 *
 * @author zhenghong
 * @date 2025/6/12
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    /**
     * 在目标 Bean 实例化（即调用构造器）之前执行。
     * <p>
     * 如果该方法返回一个非 {@code null} 的对象，那么 Spring 将跳过默认的实例化过程，
     * 直接使用返回的对象作为该 Bean 的实例。
     * <p>
     * 注意：此时 Bean 的依赖注入和初始化方法还未执行。
     *
     * @param beanClass 正在实例化的 Bean 的 Class 类型
     * @param beanName  Spring 容器中该 Bean 的名称
     * @return 要使用的 Bean 实例对象（通常是代理对象），或者 {@code null} 表示继续走默认实例化流程
     * @throws BeansException 如果创建过程出错，可以抛出该异常阻止 Bean 的进一步创建
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

}
