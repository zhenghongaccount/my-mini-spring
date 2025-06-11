package org.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * {@code Advisor} 是 Spring AOP 的核心接口之一，
 * 表示一个增强（Advice）和其应用点（Pointcut）之间的抽象关系。
 * <p>
 * 通常情况下，{@code Advisor} 会与 {@code Pointcut} 和 {@code Advice} 结合使用，
 * 构成完整的 AOP 织入逻辑。它用于封装增强逻辑，并在框架中提供统一的处理入口。
 * </p>
 * <p>
 * 如果一个 {@code Advisor} 是 {@link org.springframework.aop.PointcutAdvisor} 的实例，
 * 它还包含一个用于匹配方法的 {@code Pointcut}，否则它将作用于所有连接点（JoinPoint）。
 * </p>
 *
 * @see Advice
 * @see org.springframework.aop.PointcutAdvisor
 *
 * @author zhenghong
 * @date 2025/6/11
 */
public interface Advisor {

    Advice getAdvice();

}
