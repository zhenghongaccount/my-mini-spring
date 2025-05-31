package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * 使用CGLib创建类
 *
 * @author zhenghong
 * @date 2025/5/31
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy{
    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        //未实现
        throw new UnsupportedOperationException("CglibSubclassingInstantiationStrategy does not support instantiation");
    }
}
