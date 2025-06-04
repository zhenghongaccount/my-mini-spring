package org.springframework.beans.factory;

/**
 * InitializingBean 是 Spring 框架提供的生命周期回调接口之一，
 * 用于在 Bean 的所有属性设置完成后（即依赖注入完成后）执行自定义初始化逻辑。
 * <p>
 * 实现方式：
 * - 当一个 Bean 实现了该接口后，Spring 在实例化该 Bean 并注入所有属性之后，
 *   会自动调用其 `afterPropertiesSet()` 方法。
 * - 可用于执行一些初始化操作，如校验配置、初始化资源等。
 * <p>
 * 推荐说明：
 * - 该接口属于 Spring 专有接口，会使代码与 Spring 框架产生耦合。
 * - 如果想保持代码解耦，推荐使用标准注解 @PostConstruct。
 *
 * @author zhenghong
 * @date 2025/6/4
 */
public interface InitializingBean {

    /**
     * 在所有 Bean 属性设置完成后由 Spring 容器调用。
     *
     * @throws Exception 允许抛出异常，容器会捕获处理
     */
    void afterPropertiesSet() throws Exception;
}
