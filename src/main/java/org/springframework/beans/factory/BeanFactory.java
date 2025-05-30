package org.springframework.beans.factory;

/**
 * bean容器
 */
public interface BeanFactory {
    Object getBean(String name) throws BeansException;
}
