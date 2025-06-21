package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
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

    private final Map<String,Object> singletonObjects = new HashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    protected final Map<String, Object> earlySingletonObjects = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        Object o = singletonObjects.get(beanName);
        if (o == null) {
            o = earlySingletonObjects.get(beanName);
        }
        return o;
    }

    @Override
    public void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName,singletonObject);
    }

    protected void registerDisposableBean(String beanName, DisposableBean disposableBean){
        disposableBeans.put(beanName,disposableBean);
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
