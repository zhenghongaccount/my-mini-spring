package org.springframework.aop.framework;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * 方法调用上下文的反射实现，封装了AOP代理中的方法调用信息。
 *
 * <p>这个类实现了AOP联盟的{@link MethodInvocation}接口，提供了对以下核心元素的访问：
 * <ul>
 *   <li>目标对象（被代理的原始对象）</li>
 *   <li>被调用的方法（包括方法元数据）</li>
 *   <li>方法调用参数</li>
 * </ul>
 *
 * <h3>典型用法：</h3>
 * 主要用于Spring AOP框架内部，作为{@link org.aopalliance.intercept.MethodInterceptor}
 * 处理拦截链时的上下文对象。在自定义拦截器中可以这样使用：
 * <pre class="code">
 * public Object invoke(MethodInvocation invocation) throws Throwable {
 *   // 1. 前置处理
 *   System.out.println("Before method: " + invocation.getMethod().getName());
 * <p>
 *   // 2. 执行目标方法
 *   Object result = invocation.proceed();
 * <p>
 *   // 3. 后置处理
 *   System.out.println("After method, result: " + result);
 *   return result;
 * }
 * </pre>
 *
 * <p>注意：这个实现是<em>不可变的</em>和<em>线程安全的</em>，所有关键字段(final)在构造后都不能修改。
 *
 * @see org.springframework.aop.framework.ProxyFactory
 * @see org.aopalliance.intercept.MethodInterceptor
 *
 * @author zhenghong
 * @date 2025/6/8
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    /** 目标对象（被代理的原始对象） */
    private final Object target;

    /** 被调用的反射方法对象 */
    private final Method method;

    /** 方法调用参数数组 */
    private final Object[] arguments;

    public ReflectiveMethodInvocation(final Object target, final Method method, final Object[] arguments) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    /**
     * 继续执行拦截器链，最终调用目标方法
     *
     * <p>在简单实现中直接反射调用目标方法，实际Spring实现会处理拦截器链
     *
     * @return 目标方法的执行结果
     * @throws Throwable 如果目标方法抛出任何异常
     */
    @Override
    public Object proceed() throws Throwable {
        return method.invoke(target, arguments);
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}
