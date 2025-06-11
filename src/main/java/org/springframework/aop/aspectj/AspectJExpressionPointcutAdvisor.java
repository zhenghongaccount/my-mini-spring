package org.springframework.aop.aspectj;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;

/**
 * {@code AspectJExpressionPointcutAdvisor} 是 Spring AOP 中用于支持
 * AspectJ 表达式的 {@link PointcutAdvisor} 实现。
 * <p>
 * 该类将 {@link Advice} 和通过 AspectJ 表达式定义的 {@link Pointcut} 组合起来，
 * 从而用于创建具有灵活匹配规则的 AOP 增强。
 * </p>
 * <p>
 * 例如，可以通过表达式如 {@code execution(* com.example.service.*.*(..))} 来匹配特定方法。
 * </p>
 *
 * @see org.springframework.aop.aspectj.AspectJExpressionPointcut
 * @see org.springframework.aop.PointcutAdvisor
 *
 * @author zhenghong
 * @date 2025/6/11
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    private AspectJExpressionPointcut pointcut;

    private Advice advice;

    private String expression;

    public void setExpression(String expression) {
        this.expression = expression;
        pointcut = new AspectJExpressionPointcut(expression);
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
