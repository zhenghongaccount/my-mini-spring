package org.springframework.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配器接口（MethodMatcher），用于在 AOP 中判断某个方法是否符合切点(Pointcut)的匹配规则。
 * <p>
 * Spring AOP 的 Pointcut 通常由两部分组成：
 * - ClassFilter：判断目标类是否匹配；
 * - MethodMatcher：判断目标类中的方法是否匹配。
 * <p>
 * 如果一个类被 ClassFilter 匹配，MethodMatcher 则用于进一步判断其中哪些方法需要被增强（Advice）。
 * <p>
 * 使用场景示例：
 * - 只增强以 "get" 开头的方法；
 * - 只拦截带有某些注解的方法；
 * - 配合 AspectJ 表达式使用。
 *
 * @author zhenghong
 * @date 2025/6/7
 */
public interface MethodMatcher {

    /**
     * 判断指定的目标类中的某个方法是否匹配切点规则。
     *
     * @param method       要判断的方法（包含方法名、参数等信息）
     * @param targetClass  方法所属的目标类（可能是代理类或目标类）
     * @return true 表示方法匹配，应被 AOP 增强；false 表示不匹配
     */
    boolean matches(Method method, Class<?> targetClass);

}
