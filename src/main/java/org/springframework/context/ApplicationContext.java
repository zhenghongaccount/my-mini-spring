package org.springframework.context;

import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.io.ResourceLoader;

/**
 * ApplicationContext 是 Spring 应用上下文的核心接口，表示 Spring IoC 容器的高级形式
 * <p>
 * 他在 BeanFactory 的基础上进行了功能扩展，提供更加丰富的应用层功能支持，包括：
 * - 国际化（i18n）支持
 * - 事件发布机制（事件驱动模型）
 * - 统一的资源管理能力（如 classpath、文件、URL）等
 * - 自动注册 BeanPostProcessor 和 BeanFactoryPostProcessor
 * - 支持父子容器结构（模块化组织 Bean）
 * <p>
 * 常见的实现类：
 * - ClassPathXmlApplicationContext
 * - FileSystemXmlApplicationContext
 * - AnnotationConfigApplicationContext
 * - WebApplicationContext
 * <p>
 * ApplicationContext 是绝大多数 Spring 应用启动和管理 Bean 的首选入口。
 * <p>
 * 它继承了多个接口：
 * - ListableBeanFactory：支持按类型、名称查找多个 Bean
 * - HierarchicalBeanFactory：支持父子容器结构
 * - ResourceLoader：支持从各种资源位置加载配置（如 classpath:, file: 等）
 *
 * @author zhenghong
 * @date 2025/6/3
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
