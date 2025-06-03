package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * 提供修改和定制所有已加载 BeanDefinition 的能力。
 * <p>
 * 允许开发者在 Spring 容器实例化任何 Bean 之前，
 * 对 Bean 的配置信息（如属性值、依赖关系等）进行统一的修改或增强。
 *
 * @author zhenghong
 * @date 2025/6/2
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有 BeanDefinition 加载完成后，但在 Bean 实例化之前调用。
     * 可以通过传入的 ConfigurableListableBeanFactory 访问和修改容器中所有 BeanDefinition。
     *
     * @param beanFactory 可配置且可遍历的 BeanFactory，实现对 BeanDefinition 的访问和修改
     * @throws BeansException 修改过程中可能抛出的异常
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
