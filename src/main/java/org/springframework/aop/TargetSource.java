package org.springframework.aop;

/**
 * TargetSource 是 AOP 框架中的一个核心类，用于封装目标对象（即被代理的原始业务对象）。
 * 它提供统一的访问方式，供代理类（如 JdkDynamicAopProxy）获取目标对象及其接口信息。
 * <p>
 * 主要用途：
 * - 提供目标对象给代理逻辑调用（如反射执行原始方法）。
 * - 提供目标对象实现的接口，供 JDK 动态代理使用。
 * <p>
 * 在 JDK 动态代理中，代理对象必须实现目标对象的接口，
 * 因此 getTargetClasses() 返回的是接口数组，而不是目标类本身。
 *
 * @author zhenghong
 * @date 2025/6/8
 */
public class TargetSource {

    // 被代理的原始业务对象
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * 获取目标对象所实现的接口数组。
     * 这是 JDK 动态代理所必需的，因为它只能代理接口。
     *
     * @return 目标对象实现的接口列表（Class 类型数组）
     */
    public Class<?>[] getTargetClasses() {
        return target.getClass().getInterfaces();
    }

    public Object getTarget() {
        return target;
    }
}
