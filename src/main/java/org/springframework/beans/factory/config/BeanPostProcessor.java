package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;

/**
 * 提供在 bean 初始化之前和之后通过 BeanPostProcessor 修改 bean 的能力，
 * 这是一个扩展接口，供开发者实现，用于对容器中的 bean 进行功能增强和包装（如 AOP、注入等）
 * spring 内部只通过 AutowiredCapableBeanFactory 来调用它
 *
 * @author zhenghong
 * @date 2025/6/2
 */
public interface BeanPostProcessor {

    /**
     * 在 bean 初始化方法(如 @PostConstruct) 执行之前调用
     * @param bean 已经创建并设置属性的 bean 实例
     * @param beanName bean 的名称
     * @return 可原样返回或者是增强的代理对象
     * @throws BeansException 可抛出异常以阻止后续初始化
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在 bean 初始化方法执行之后调用。
     * 通常用于生成代理、增强功能等后处理逻辑。
     *
     * @param bean 初始化完成的 bean 实例
     * @param beanName bean 的名称
     * @return 要返回的 bean 实例（可原样返回或替换为增强后的对象）
     * @throws BeansException 可抛出异常以终止 bean 的创建
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
