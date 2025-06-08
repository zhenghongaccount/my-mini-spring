package org.springframework.aop.framework;

/**
 * AOP代理抽象
 * <p>
 * 这个接口是 AOP 代理对象的统一抽象，定义了生成代理对象的标准方法。
 * 不关心代理实现方式（JDK 动态代理 或 CGLIB），只关心“如何获取代理对象”。
 * <p>
 * 实现类：
 * - JdkDynamicAopProxy：基于 JDK 的接口代理实现。
 * - CglibAopProxy（如存在）：基于字节码的类代理实现。
 * <p>
 * 使用场景：
 * 在代理工厂中（如 ProxyFactory）根据目标对象和配置，创建 AopProxy 实例，然后调用 getProxy() 获取代理对象。
 */
public interface AopProxy {

    /**
     * 获取代理对象。
     *
     * @return 被代理后的对象，具有增强功能。
     */
    Object getProxy();

}
