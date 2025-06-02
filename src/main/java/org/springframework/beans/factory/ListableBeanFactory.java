package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

import java.util.Map;

/**
 * 提供查找指定bean和BeanDefinition的能力
 *
 * @author zhenghong
 * @date 2025/6/2
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 人居classType查找bean
     * @param type classType
     * @return Map<String,T>
     * @param <T> T
     * @throws BeansException 抛出BeansException
     */
    <T> Map<String,T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 返回定义的所有bean的名称
     *
     * @return 所有bean的名称
     */
    String[] getBeanDefinitionNames();
}
