package org.springframework.beans.factory;

/**
 * 标记性接口，表示一个 Bean 具有“感知”Spring容器中某些资源的能力。
 *
 * <p>Spring 容器会在 Bean 初始化过程中识别实现了特定 Aware 子接口的 Bean，
 * 并通过回调方法将相应的资源注入进去，如 BeanFactory、ApplicationContext、Environment 等。
 *
 * <p>常见的子接口包括：
 * <ul>
 *   <li>{@link BeanNameAware}：注入当前 Bean 的名称</li>
 *   <li>{@link BeanFactoryAware}：注入当前 Bean 所属的 BeanFactory</li>
 *   <li>{@link ApplicationContextAware}：注入当前 ApplicationContext（Spring 上下文）</li>
 * </ul>
 *
 * <p>注意：Aware 接口本身没有方法，只作为扩展点的标记；真正的注入由其子接口完成。
 *
 * @see BeanFactoryAware
 * @see ApplicationContextAware
 * @see BeanNameAware
 *
 * @author zhenghong
 * @date 2025/6/5
 */
public interface Aware {
}
