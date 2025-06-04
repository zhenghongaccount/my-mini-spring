package org.springframework.context;

import org.springframework.beans.BeansException;

/**
 * ConfigurableApplicationContext 是一个可配置的 Spring 应用上下文接口，
 * 扩展了 ApplicationContext,提供对容器生命周期的精细控制能力
 * 其最核心的方法是 {@link #refresh()}，用于手动刷新容器：
 * - 清除并销毁原有的 Bean 实例
 * - 重新加载配置（如 XML、注解 Bean）
 * - 初始化并注册所有单例 Bean
 * - 触发相关生命周期回调（如 BeanPostProcessor、ApplicationListener 等）
 * <p>
 * 使用场景包括：
 * - 动态重新加载配置（热部署、配置中心）
 * - 框架/工具开发中控制容器生命周期
 * - 测试场景中复用容器
 * <p>
 * 常见实现类：
 * - {@link org.springframework.context.support.ClassPathXmlApplicationContext}
 * - {@link org.springframework.context.annotation.AnnotationConfigApplicationContext}
 *
 * 这是 Spring Boot 启动流程中的关键接口之一。
 *
 * @see ApplicationContext
 * @see org.springframework.context.support.AbstractApplicationContext
 *
 * @author zhenghong
 * @date 2025/6/3
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器：重新加载 Bean 定义并重新创建所有非懒加载的单例 Bean。
     *
     * @throws BeansException 如果刷新过程中出现错误
     */
    void refresh() throws BeansException;

    /**
     * 关闭容器：释放资源并销毁所有单例 Bean。
     * <p>
     * - 会执行所有注册的销毁回调，如 DisposableBean、destroy-method
     * - 发布 ContextClosedEvent 事件
     * - 释放 ApplicationContext 中注册的所有资源（如线程池、文件句柄）
     * <p>
     * 手动关闭应用上下文的场景包括测试、脚本任务等。
     *
     * @throws BeansException 如果关闭过程中出现错误
     */
    void close() throws BeansException;

    /**
     * 注册 JVM 关闭钩子：当 JVM 正常关闭时，自动调用 close() 方法以销毁容器。
     * <p>
     * 通常用于独立应用（如 Spring Boot 主程序）中，确保退出时资源被正确释放。
     * <p>
     * 示例：
     *   context.registerShutdownHook();
     */
    void registerShutdownHook();

}
