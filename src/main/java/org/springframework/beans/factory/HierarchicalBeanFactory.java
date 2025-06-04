package org.springframework.beans.factory;

/**
 * HierarchicalBeanFactory 是 Spring 框架中的一个扩展接口，
 * 他在基本的 BeanFactory 功能之上，引入了"父 BeanFactory"的概念，
 * 用于支持 BeanFactory 之间的层级接口（父子容器）。
 * <p>
 * 通过这个接口，子容器可以访问父容器中定义的 Bean，但是父容器无法访问子容器中的 Bean
 * 这对于实现模块化、分层管理 Bean 定义，尤其在 Web 应用中非常有用（例如 RootContext 与 Web 子容器）。
 *
 * @author zhenghong
 * @date 2025/6/3
 */
public interface HierarchicalBeanFactory extends BeanFactory {
}
