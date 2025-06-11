package org.springframework.aop;

import java.lang.reflect.Method;

/**
 * 表示一种“前置通知”的 AOP 增强类型，在目标方法执行之前执行相关逻辑。
 * <p>
 * 这是 Spring AOP 中的一种 Advice 类型，用于在目标方法调用前进行增强处理，
 * 例如：权限检查、日志记录、参数验证等。
 * </p>
 * <p>
 * 实现该接口的类将会被 Spring AOP 在目标方法执行前自动调用 {@code before} 方法。
 * </p>
 *
 * @see org.springframework.aop.BeforeAdvice
 * @see org.aopalliance.aop.Advice
 *
 * @author zhenghong
 * @date 2025/6/11
 */
public interface MethodBeforeAdvice extends BeforeAdvice{

    /**
     * 在目标方法执行之前调用的增强逻辑。
     *
     * @param method 被调用的方法对象
     * @param args   方法参数
     * @param target 目标对象（方法所属的实例）
     * @throws Throwable 如果在前置处理逻辑中发生异常，可以抛出
     */
    void before(Method method, Object[] args, Object target) throws Throwable;

}
