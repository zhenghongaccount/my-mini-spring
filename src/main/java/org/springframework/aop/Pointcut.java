package org.springframework.aop;

/**
 * Pointcut（切点）接口是 AOP 的核心概念之一，用于定义哪些类和方法需要被 AOP 增强（Advice）。
 * <p>
 * 一个 Pointcut 包含两个部分：
 * 1. {@link ClassFilter}：用于判断目标类是否匹配；
 * 2. {@link MethodMatcher}：用于判断目标类中的方法是否匹配。
 * <p>
 * 当类和方法都满足条件时，对应的 Advice（通知）才会被应用到该方法上。
 * <p>
 * Pointcut 通常通过 AspectJ 表达式进行定义，也可以手动实现以实现自定义匹配逻辑。
 * <p>
 * 使用示例场景：
 * - 拦截某个包下所有类中的 public 方法；
 * - 只增强方法名以 "get" 开头的方法；
 * - 只对标注了特定注解的方法进行增强。
 */
public interface Pointcut {

    /**
     * 获取用于判断类是否匹配的 ClassFilter。
     *
     * @return 用于类匹配的 {@link ClassFilter} 实现
     */
    ClassFilter getClassFilter();

    /**
     * 获取用于判断方法是否匹配的 MethodMatcher。
     *
     * @return 用于方法匹配的 {@link MethodMatcher} 实现
     */
    MethodMatcher getMethodMatcher();

}
