package org.springframework.beans.factory.support;

import org.springframework.beans.factory.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * 实例化策略接口，实现他的子类可能是CGLib或者是动态代理创建类，所以需要这个接口调用不同的实例化策略
 *
 * @author zhenghong
 * @date 2025/5/31
 */
public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition) throws BeansException;
}
