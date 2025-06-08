package org.springframework.aop;

/**
 * 类过滤器接口（ClassFilter）用于在 AOP 编程中判断一个类是否符合切点(Pointcut)匹配条件。
 * <p>
 * Spring AOP 中的 Pointcut 由 ClassFilter 和 MethodMatcher 两部分组成：
 * - ClassFilter 用于判断类是否匹配。
 * - MethodMatcher 用于判断该类中的方法是否匹配。
 * <p>
 * 如果一个类被 ClassFilter 匹配上（即 matches 方法返回 true），
 * 则 MethodMatcher 会进一步判断其方法是否需要被增强（Advice）。
 * <p>
 * 使用场景示例：
 * - 只对类名中包含 "Service" 的类应用 AOP 增强
 * - 只对特定包下的类启用切面逻辑
 */
public interface ClassFilter {

    /**
     * 判断指定的类是否匹配当前的切点规则。
     *
     * @param clazz 要匹配的类对象
     * @return true 表示该类匹配，false 表示不匹配
     */
    boolean matches(Class<?> clazz);

}
