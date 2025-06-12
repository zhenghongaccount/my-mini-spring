package org.springframework.aop.framework.autoproxy;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.*;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Collection;

/**
 * {@code DefaultAdvisorAutoProxyCreator} 是一个 Spring 容器中的自动代理创建器，
 * 实现了 {@link InstantiationAwareBeanPostProcessor} 和 {@link BeanFactoryAware}。
 *
 * <p>它在 Bean 实例化之前拦截创建过程，根据容器中定义的 {@link AspectJExpressionPointcutAdvisor} 切面，
 * 判断是否需要为该 Bean 创建代理对象。如果匹配，则使用 {@link ProxyFactory} 创建 AOP 代理。
 *
 * <p>此类在 Spring AOP 实现中扮演重要角色，帮助自动将符合条件的 Bean 包装成代理对象。
 *
 * @author zhenghong
 * @date 2025/6/12
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    /**
     * 判断当前类是否为 Spring AOP 的基础设施类（Advice、Pointcut、Advisor），
     * 这些类不应该被代理。
     *
     * @param beanClass 要检查的类
     * @return 如果是 AOP 基础设施类返回 true，否则返回 false
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || Pointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }

    /**
     * 在 Bean 实例化之前执行，用于判断是否需要为该 Bean 创建代理对象。
     * 如果匹配到某个切面（Advisor），则创建代理对象并返回。
     *
     * @param beanClass 要实例化的 Bean 的类
     * @param beanName Bean 名称
     * @return 原始对象或代理对象，如果返回非 null，则跳过后续实例化过程
     * @throws BeansException 创建代理过程中出现异常时抛出
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (isInfrastructureClass(beanClass)) {
            return null;
        }

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        try {
            for (AspectJExpressionPointcutAdvisor advisor : advisors) {
                ClassFilter classFilter = advisor.getPointcut().getClassFilter();
                if (classFilter.matches(beanClass)) {
                    AdvisedSupport advisedSupport = new AdvisedSupport();

                    BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                    Object instantiate = beanFactory.getInstantiationStrategy().instantiate(beanDefinition);
                    TargetSource targetSource = new TargetSource(instantiate);
                    advisedSupport.setTargetSource(targetSource);
                    advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
                    advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                    return new ProxyFactory(advisedSupport).getProxy();
                }
            }
        } catch (Exception ex) {
            throw new BeansException("Error create proxy bean for: " + beanName, ex);
        }
        return null;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
