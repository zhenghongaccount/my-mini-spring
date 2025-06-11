package org.springframework.aop;

/**
 * {@code PointcutAdvisor} 是 Spring AOP 框架中的一个核心接口，
 * 表示一个带有 Pointcut 的 Advisor。
 * <p>
 * 相比基础 {@link Advisor} 接口只包含 {@link org.aopalliance.aop.Advice}，
 * {@code PointcutAdvisor} 还包含一个 {@link Pointcut}，用于指定 Advice 应用的 JoinPoint 范围。
 * </p>
 * <p>
 * 通常用于结合 {@code AspectJExpressionPointcut} 表达式，定义在哪些方法上应用切面逻辑。
 * </p>
 *
 * @see Advisor
 * @see Pointcut
 */
public interface PointcutAdvisor extends Advisor{

    /**
     * 获取与此 Advisor 关联的 Pointcut。
     * <p>
     * Pointcut 用于定义 Advice 应该应用到哪些连接点（JoinPoint），
     * 比如通过方法签名、注解、包名等进行匹配。
     * </p>
     *
     * @return 用于方法匹配的 Pointcut 实例
     */
    public Pointcut getPointcut();

}
