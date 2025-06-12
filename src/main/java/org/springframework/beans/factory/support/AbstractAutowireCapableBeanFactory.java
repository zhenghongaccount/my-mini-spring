package org.springframework.beans.factory.support;

import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.*;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 抽象的自动装配 Bean 工厂，是 BeanFactory 的核心实现之一。
 * <p>
 * 功能：
 * - 提供完整的 Bean 创建流程，包括实例化、属性填充、初始化以及应用后处理器。
 * - 支持自动装配（autowire）功能。
 * - 支持 BeanPostProcessor 的前置和后置处理机制
 * <p>
 * 它是 Spring Bean 生命周期管理的核心部分，通常不会被直接使用，而是作为底层支持被扩展。
 *
 * @author zhenghong
 * @date 2025/5/31
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowiredCapableBeanFactory {

    public InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String name, BeanDefinition beanDefinition) throws BeansException {
        // 如果 bean 需要代理，直接返回代理对象
        Object bean = resolveBeforeInstantiation(name, beanDefinition);
        if (bean != null) {
            return bean;
        }
        return doCreateBean(name, beanDefinition);
    }

    /**
     * 尝试在标准实例化流程之前解析 bean 实例。
     *
     * <p>主要目的是允许某些 {@link InstantiationAwareBeanPostProcessor} 实现
     * （如 AOP 自动代理器）返回一个代理对象，提前跳过后续的默认创建流程。
     *
     * @param beanName bean 的名称
     * @param beanDefinition bean 的定义信息
     * @return 提前解析得到的对象，如果返回 null，则继续执行默认的创建流程
     */
    protected Object resolveBeforeInstantiation(final String beanName, final BeanDefinition beanDefinition) throws BeansException {
        // 调用 InstantiationAwareBeanPostProcessor 的 postProcessBeforeInstantiation 方法
        // 尝试提前返回一个对象（比如代理）
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        // 如果有处理器返回了非 null 的 bean（比如 AOP 生成的代理对象）
        if (bean != null) {
            // 再调用所有 BeanPostProcessor 的 postProcessAfterInitialization 方法
            // 对这个提前返回的 bean 进行初始化后的增强处理
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    /**
     * 调用所有 {@link InstantiationAwareBeanPostProcessor} 的
     * {@code postProcessBeforeInstantiation} 方法，允许其提前返回一个自定义对象。
     *
     * <p>这是 Spring 提供的一个“跳过默认实例化流程”的入口。
     * 如果任一处理器返回了非 null，则直接使用该对象作为最终的 bean 实例。
     *
     * @param beanClass 当前正在创建的 bean 的类
     * @param beanName bean 的名称
     * @return 提前返回的 bean 实例（如代理对象），或 null 表示不干预
     *
     * @author zhenghong
     * @date 2025/6/12
     */
    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * 实际的创建逻辑：
     * 1. 实例化 Bean
     * 2. 填充属性（依赖注入）
     * 3. 执行初始化方法、调用 BeanPostProcessor
     * 4. 注册为单例
     */
    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) throws BeansException{
        Class<?> beanClass = beanDefinition.getBeanClass();
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition);
            //为 bean 设置属性值
            applyPropertyValues(beanName, bean, beanDefinition);
            //执行 bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
            initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        if (beanDefinition.isSingleton()) {
            addSingleton(beanName, bean);
        }

        return bean;
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        //只有 singleton 类型才会执行销毁方法
        if (beanDefinition.isSingleton()) {
            if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
                registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
            }
        }
    }

    /**
     * 初始化 Bean，包括：
     * 1. 调用 BeanPostProcessor 的前置方法
     * 2. 执行初始化方法（如自定义 init-method 或 InitializingBean）
     * 3. 调用 BeanPostProcessor 的后置方法
     */
    protected Object initializeBean(String beanName,Object bean,BeanDefinition beanDefinition) {
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }

        //执行BeanPostProcessor的前置处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Throwable ex) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", ex);
        }

        //执行BeanPostProcessor的后置处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(existingBean, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessAfterInitialization(existingBean, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    /**
     * 为bean填充属性
     *
     * @param bean bean
     * @param beanDefinition bean 信息
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            if (propertyValues == null) {
                return;
            }
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                if (value instanceof BeanReference beanReference) {
                    String referenceBeanName = beanReference.getBeanName();
                    value = getBean(referenceBeanName);
                }

                //通过 HuTool 经过反射设置属性而不是通过 set 方法
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values for bean: " + beanName, e);
        }
    }

    public Object createBeanInstance(BeanDefinition beanDefinition) {
        return getInstantiationStrategy().instantiate(beanDefinition);
    }

    /**
     * bean 的初始化方法
     * @param beanName 需要初始化的 bean 的名字
     * @param bean 需要初始化的 bean
     * @param beanDefinition 需要初始化的 bean 的 beanDefinition
     */
    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Throwable{
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod = ClassUtil.getPublicMethod(beanDefinition.getBeanClass(), initMethodName);
            if (initMethod == null) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(bean);
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
