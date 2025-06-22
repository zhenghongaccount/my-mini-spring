package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * SingletonBeanRegistry 接口的默认实现。
 * 此注册表包含一个以 Bean 名称为键的单例 Bean 实例映射。
 * 它提供了检索和注册单例的基本方法。
 *
 * @author zhenghong
 * @date 2025/5/31
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    // 一级缓存
    private final Map<String,Object> singletonObjects = new HashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    // 二级缓存
    protected final Map<String, Object> earlySingletonObjects = new HashMap<>();

    // 三级缓存
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        Object singletonObject = singletonObjects.get(beanName);
        if (singletonObject == null) {
            singletonObject = earlySingletonObjects.get(beanName);
            if (singletonObject == null) {
                ObjectFactory<?> objectFactory = singletonFactories.get(beanName);
                if (objectFactory != null) {
                    singletonObject = objectFactory.getObject();
                    // 将三级缓存中的内容放进二级缓存
                    earlySingletonObjects.put(beanName, singletonObject);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    @Override
    public void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName,singletonObject);
    }

    protected void registerDisposableBean(String beanName, DisposableBean disposableBean){
        disposableBeans.put(beanName,disposableBean);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> objectFactory){
        singletonFactories.put(beanName,objectFactory);
    }

    public void destroySingletons(){
        Set<String> beanNames = disposableBeans.keySet();
        for (String beanName : beanNames){
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e){
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
