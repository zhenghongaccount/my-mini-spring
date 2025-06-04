package org.springframework.beans.factory.support;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * DisposableBeanAdapter 是 Spring 内部用于适配 bean 销毁逻辑的适配器类。
 * <p>
 * 它统一封装了两种 Bean 销毁机制：
 * 1. 实现了 DisposableBean 接口的 destroy() 方法
 * 2. 在 BeanDefinition 中指定的自定义销毁方法（destroy-method）
 * <p>
 * 容器在销毁 Bean 时不直接调用 Bean 本身的方法，而是调用此适配器的 destroy() 方法，
 * 由它内部判断并调用正确的销毁逻辑，防止重复销毁并支持灵活配置。
 * <p>
 * 使用场景：由 Spring 容器在注册销毁回调时自动使用，不需用户直接调用。
 *
 * @author zhenghong
 * @date 2025/6/4
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;

    private final String beanName;

    private final String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        //避免同时继承自DisposableBean，且自定义方法与DisposableBean方法同名，销毁方法执行两次的情况
        if (StrUtil.isNotEmpty(this.destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))) {
            //执行自定义方法
            Method destroyMethod = ClassUtil.getPublicMethod(bean.getClass(), this.destroyMethodName);
            if (destroyMethod == null) {
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            destroyMethod.invoke(bean);
        }
    }
}
