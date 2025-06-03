package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;

/**
 * 提供在 bean 初始化之前和之后通过 BeanPostProcessor 修改 bean 的能力，
 * 调用 BeanPostProcessors 的 postProcessBeforeInitialization 方法
 * <p>
 * 此接口是 Spring 容器的高级扩展接口，用于对已经创建但尚未初始化的 bean 应用自定义的处理逻辑，
 * 常用于实现自动注入、代理、属性增强等功能。
 *
 * @author zhenghong
 * @date 2025/6/2
 */
public interface AutowiredCapableBeanFactory extends BeanFactory {

    /**
     * 应用所有已注册的 BeanPostProcessor 的 postProcessBeforeInitialization 方法。
     * 执行 BeanPostProcessor 的 postProcessAfterInitialization 方法
     * <p>
     * 该方法在 bean 初始化方法（如 @PostConstruct 或 afterPropertiesSet）执行之前调用，
     * 用于对 bean 进行属性设置、字段注入或其他前置处理操作。
     *
     * @param bean 当前已实例化但尚未初始化的 bean 实例
     * @param beanName bean 在容器中的名称
     * @return 原始 bean，也可能是经过增强或包装的 bean
     * @throws BeansException 如果处理过程中发生异常
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 应用所有已注册的 BeanPostProcessor 的 postProcessAfterInitialization 方法。
     * <p>
     * 该方法在 bean 的初始化方法执行完成之后调用，通常用于生成代理对象（如 AOP）或增强 bean 的行为。
     *
     * @param bean 已初始化完成的 bean 实例
     * @param beanName bean 在容器中的名称
     * @return 原始 bean，也可能是经过代理或增强的 bean
     * @throws BeansException 如果处理过程中发生异常
     */
    Object applyBeanPostProcessorsAfterInitialization(Object bean, String beanName) throws BeansException;
}
