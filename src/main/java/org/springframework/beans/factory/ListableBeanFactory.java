package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

import java.util.Map;

/**
 * 返回指定类型的bean实例
 *
 * @author zhenghong
 * @date 2025/6/2
 */
public interface ListableBeanFactory extends BeanFactory {
    <T> Map<String,T> getBeansOfType(Class<T> type) throws BeansException;
}
