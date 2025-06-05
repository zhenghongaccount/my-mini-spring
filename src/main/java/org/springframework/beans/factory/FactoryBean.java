package org.springframework.beans.factory;

/**
 * FactoryBean 是 Spring 框架提供的一个用于自定义 Bean 创建逻辑的接口。
 * <p>
 * 与普通 Bean 不同，实现了 FactoryBean 接口的类，会被 Spring 容器特殊处理：
 * 当从容器中通过 getBean() 获取该 Bean 时，返回的是 getObject() 方法的返回值，
 * 而不是 FactoryBean 本身。如果想获取 FactoryBean 实例本身，需要加上 "&" 前缀。
 * <p>
 * 用途：
 * - 用于创建复杂对象（如代理对象、第三方类库对象、需要初始化逻辑的对象等）
 * - 允许开发者以编程方式控制 Bean 的创建过程
 *
 * @param <T> 表示由该 FactoryBean 创建的对象的类型
 *
 * @author zhenghong
 * @date 2025/6/5
 */
public interface FactoryBean<T> {

    /**
     * 返回由该 FactoryBean 创建的对象实例。
     * <p>
     * Spring 容器调用此方法来获取实际要注入或使用的 Bean。
     * 可以在此方法中自定义实例化逻辑，如返回代理对象、构造复杂依赖的对象等。
     *
     * @return T 创建的对象
     * @throws Exception 如果对象创建过程中发生异常
     */
    T getObject() throws Exception;

    /**
     * 指示由 getObject() 返回的对象是否是单例。
     * <p>
     * 如果返回 true，Spring 容器会缓存该对象，相当于 singleton 模式；
     * 如果返回 false，每次调用 getBean() 都会重新调用 getObject() 创建新实例。
     *
     * @return true 表示是单例，false 表示是原型
     */
    boolean isSingleton();
}
