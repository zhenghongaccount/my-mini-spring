package org.springframework.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.AdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK 动态代理的核心实现类，用于创建基于接口的 AOP 代理对象。
 *
 * <p>本类实现了 Spring AOP 框架中两种关键接口：
 * <ul>
 *   <li>{@link AopProxy}：提供获取代理对象的统一入口</li>
 *   <li>{@link InvocationHandler}：处理代理对象的方法调用</li>
 * </ul>
 *
 * <h3>典型用法：</h3>
 * 通常通过 {@link org.springframework.aop.framework.ProxyFactory} 间接使用，
 * 而非直接实例化。典型工作流程：
 * <pre class="code">
 * // 1. 创建配置中心
 * AdvisedSupport advisedSupport = new AdvisedSupport();
 * advisedSupport.setTarget(new MyServiceImpl());
 * advisedSupport.setMethodInterceptor(new MyMethodInterceptor());
 * <p>
 * // 2. 创建代理
 * JdkDynamicAopProxy proxyFactory = new JdkDynamicAopProxy(advisedSupport);
 * MyService proxy = (MyService) proxyFactory.getProxy();
 * <p>
 * // 3. 使用代理
 * proxy.doSomething(); // 将触发拦截逻辑
 * </pre>
 *
 * <p><strong>注意：</strong>仅支持基于接口的代理，对于类代理请使用 {@link ObjenesisCglibAopProxy}。
 *
 * @see org.springframework.aop.framework.ProxyFactory
 * @see java.lang.reflect.Proxy
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    /**
     * AOP 配置中心，包含目标对象、拦截器链等配置信息
     */
    private final AdvisedSupport advisedSupport;

    public JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    /**
     * 代理对象的方法调用入口，实现 AOP 拦截逻辑
     *
     * <p>执行流程：
     * <ol>
     *   <li>检查当前方法是否匹配切点规则</li>
     *   <li>若匹配，通过方法拦截器链执行增强逻辑</li>
     *   <li>若不匹配，直接反射调用目标方法</li>
     * </ol>
     *
     * @param proxy  代理对象实例
     * @param method 被调用的方法元数据
     * @param args   方法参数
     * @return 方法执行结果
     * @throws Throwable 目标方法或拦截器抛出的异常
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 如果匹配成功，即该方法需要 aop 增强，就执行methodInterceptor.invoke
        if (advisedSupport.getMethodMatcher().matches(method,advisedSupport.getTargetSource().getTarget().getClass())) {
            MethodInterceptor methodInterceptor = advisedSupport.getMethodInterceptor();
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(advisedSupport.getTargetSource().getTarget(), method, args));
        }
        // 如果匹配失败，即不需要该方法增强，直接调用不做增强
        return method.invoke(advisedSupport.getTargetSource().getTarget(), args);
    }

    /**
     * 创建代理对象实例
     *
     * <p>通过 JDK {@link Proxy#newProxyInstance} 动态生成代理类，
     * 该代理类会实现目标对象的所有接口。
     *
     * @return 代理对象实例
     * @see java.lang.reflect.Proxy#newProxyInstance
     */
    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(
                getClass().getClassLoader(),
                advisedSupport.getTargetSource().getTargetClasses(),
                this
        );
    }
}
