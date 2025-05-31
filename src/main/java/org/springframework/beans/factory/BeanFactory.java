package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

/**
 * bean容器
 */
public interface BeanFactory {
    Object getBean(String name) throws BeansException;
}
