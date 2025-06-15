package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;

import java.util.*;

/**
 * DefaultListableBeanFactory 是 Spring 容器中最核心的 BeanFactory 实现之一，
 * 具备完整的 Bean 定义注册、Bean 实例创建和自动装配能力。
 * <p>
 * 本类实现了 BeanDefinitionRegistry 接口用于注册 Bean 定义，
 * 实现了 ConfigurableListableBeanFactory 接口用于提供更强的 Bean 工厂功能。
 * 同时继承了 AbstractAutowireCapableBeanFactory，从而具备了自动注入的能力。
 * <p>
 * 简单来说：这是一个既能注册 Bean 定义，又能创建和管理 Bean 实例的完整容器实现。
 * <p>
 * 类比于 mini-spring 中的“Default 容器”实现。
 *
 * @author zhenghong
 * @date 2025/5/31
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory,BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("No bean with name '" + beanName + "' found");
        }
        return beanDefinition;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    /**
     * 提前初始化所有的单例 Bean（即容器启动时一次性创建所有非懒加载单例 Bean）
     * 触发所有 Bean 的创建过程
     *
     * @throws BeansException 创建失败时抛出异常
     */
    @Override
    public void preInstantiateSingletons() throws BeansException {
        this.beanDefinitionMap.keySet().forEach(this::getBean);
    }

    /**
     * 获取容器中所有指定类型（或其子类/实现类）的 Bean
     *
     * @param type 目标类型
     * @return Map：beanName -> Bean 实例
     * @throws BeansException 获取过程中可能抛出异常
     */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        HashMap<String, T> result = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            if (type.isAssignableFrom(beanDefinition.getBeanClass())) {
                T bean = (T) getBean(beanName);
                result.put(beanName, bean);
            }
        });
        return result;
    }

    public  <T> T getBean(Class<T> requiredType) {
        List<String> beanNames = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            Class<?> beanClass = entry.getValue().getBeanClass();
            if (requiredType.isAssignableFrom(beanClass)) {
                beanNames.add(entry.getKey());
            }
        }
        if (beanNames.size() == 1) {
            return requiredType.cast(getBean(beanNames.get(0)));
        }

        throw new BeansException(requiredType + "expected single bean but found " +
                beanNames.size() + ": " + beanNames);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        Set<String> names = beanDefinitionMap.keySet();
        return names.toArray(new String[0]);
    }
}
