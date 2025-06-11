package org.springframework.aop.framework.adapter;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.MethodBeforeAdvice;

/**
 * {@code MethodBeforeAdviceInterceptor} 是 Spring AOP 中的一个适配器类，
 * 它将 {@link MethodBeforeAdvice} 类型的前置通知适配为 {@link MethodInterceptor} 接口，
 * 从而使其能够被 AOP 框架统一处理。
 * <p>
 * 本类的核心作用是：在目标方法执行之前调用用户实现的 {@code MethodBeforeAdvice} 的 {@code before()} 方法。
 * </p>
 *
 * @see MethodBeforeAdvice
 * @see MethodInterceptor
 *
 * @author zhenghong
 * @date 2025/6/11
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {

    private final MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    /**
     * 拦截方法调用的核心逻辑。
     * <p>
     * 在目标方法执行前，先调用 {@link MethodBeforeAdvice#before} 方法执行前置通知逻辑，
     * 然后通过 {@code methodInvocation.proceed()} 继续执行原始方法。
     * </p>
     *
     * @param methodInvocation 方法调用上下文，封装了方法、参数、目标对象等信息
     * @return 返回目标方法的执行结果
     * @throws Throwable 若前置通知或目标方法执行中抛出异常
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        // 在执行被代理方法之前，先执行前置通知逻辑
        this.advice.before(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return methodInvocation.proceed();
    }
}
