package org.springframework.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * AdvisedSupport 是 AOP 框架中的核心配置类，封装了生成代理对象所需的全部元信息。
 * 它通常作为代理对象构建的“配置载体”，在运行时由 AOP 代理类读取这些配置来完成方法拦截。
 * <p>
 * ✅ 主要职责：
 * 1. 保存目标对象（TargetSource）
 * 2. 保存方法拦截器（MethodInterceptor）
 * 3. 保存方法匹配器（MethodMatcher，用于决定哪些方法需要被拦截）
 * <p>
 * ✅ 使用场景：
 * 在创建 AOP 代理对象（如 JdkDynamicAopProxy）时，将此类作为配置传入，代理类根据这些信息判断：
 * - 要代理哪个目标对象？
 * - 哪些方法需要拦截？
 * - 应该应用哪个拦截器？
 * <p>
 * ⚙️ 示例流程：
 * - 用户配置 AdvisedSupport
 * - 代理类使用 getTargetSource(), getMethodInterceptor(), getMethodMatcher() 获取配置信息
 * - 在 invoke() 时决定是否执行增强逻辑
 */
public class AdvisedSupport {

    private boolean proxyTargetClass = false;

    // 封装被代理的目标对象（以及其类型信息）
    private TargetSource targetSource;

    // 方法拦截器，用于在调用目标方法时添加横切逻辑（如日志、事务）
    private MethodInterceptor methodInterceptor;

    // 方法匹配器，决定哪些方法应该被拦截（基于切点表达式）
    private MethodMatcher methodMatcher;

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
