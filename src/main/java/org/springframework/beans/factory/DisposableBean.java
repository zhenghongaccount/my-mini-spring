package org.springframework.beans.factory;

/**
 * DisposableBean 是 Spring 框架定义的一个接口，
 * 用于在 Bean 被销毁（容器关闭时）执行自定义的清理逻辑。
 * <p>
 * 使用方式：
 * - 当一个 Bean 实现了该接口时，Spring 在销毁该 Bean 时会自动调用其 destroy() 方法。
 * - 适用于资源释放、连接关闭等操作。
 * <p>
 * 注意事项：
 * - 这是 Spring 的生命周期回调接口，存在一定的耦合性。
 * - 建议在新项目中优先使用 @PreDestroy 注解代替。
 *
 * @author zhenghong
 * @date 2025/6/4
 */
public interface DisposableBean {

    /**
     * 销毁 Bean 时调用的方法。可在此方法中编写资源清理逻辑。
     *
     * @throws Exception 可以抛出异常，Spring 容器会捕获并处理
     */
    void destroy() throws Exception;
}
