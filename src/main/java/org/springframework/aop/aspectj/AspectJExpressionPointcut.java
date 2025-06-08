package org.springframework.aop.aspectj;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * {@code AspectJExpressionPointcut} 是 Spring AOP 的一个实现，
 * 用于基于 AspectJ 表达式定义切点（Pointcut）。
 *
 * <p>该类实现了 {@link Pointcut}、{@link ClassFilter} 和 {@link MethodMatcher} 接口，
 * 可用于判断某个类或方法是否匹配指定的 AspectJ 切点表达式。</p>
 *
 * <p>典型用法：</p>
 * <pre>{@code
 * Pointcut pointcut = new AspectJExpressionPointcut("execution(* com.example.service..*(..))");
 * }</pre>
 *
 * @author zhenghong
 * @date 2025/6/8
 */
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

    /** AspectJ 表达式解析后的 PointcutExpression 对象 */
    private final PointcutExpression pointcutExpression;

    /** 支持的切点原语（Pointcut Primitives），可以扩展以支持更多语法 */
    private final static Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<>();

    static {
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    /**
     * 构造函数，接受一个 AspectJ 表达式字符串并解析为 PointcutExpression。
     *
     * @param expression AspectJ 切点表达式（例如："execution(* com.example..*(..))"）
     */
    public AspectJExpressionPointcut(String expression) {
        PointcutParser pointcutParser =
                PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
                        SUPPORTED_PRIMITIVES,
                        this.getClass().getClassLoader()
                );
        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    /**
     * 判断给定的类是否可能包含匹配的连接点（Join Point）。
     *
     * @param clazz 要检查的类
     * @return 若该类可能包含匹配的连接点，返回 true
     */
    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    /**
     * 判断给定的方法是否匹配当前的切点表达式。
     *
     * @param method      方法对象
     * @param targetClass 目标类
     * @return 如果切点表达式总是匹配该方法，返回 true
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
