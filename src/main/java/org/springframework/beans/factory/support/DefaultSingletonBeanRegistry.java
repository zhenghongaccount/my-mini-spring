package org.springframework.beans.factory.support;

import org.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * SingletonBeanRegistry 接口的默认实现。
 * 此注册表包含一个以 Bean 名称为键的单例 Bean 实例映射。
 * 它提供了检索和注册单例的基本方法。
 *
 * @author zhenghong
 * @date 2025/5/31
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private Map<String,Object> singletonObjects = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    protected void addSingleTon(String beanName, Object singletonObject){
        singletonObjects.put(beanName,singletonObject);
    }
}
