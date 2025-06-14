package org.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * {@code ClassPathScanningCandidateComponentProvider} 是一个简化版的类路径扫描器，
 * 用于扫描指定包下被 {@code @Component} 注解标记的类，并将其封装为 {@link BeanDefinition} 对象。
 * <p>
 * 该类依赖 Hutool 的 {@code ClassUtil} 工具来扫描包中的类，适用于模拟或简化 Spring 的组件扫描过程。
 * <p>
 * ⚠ 注意：与 Spring 原生的扫描机制不同，这里通过类加载实现，适合轻量级或自定义容器使用。
 *
 * <p><b>示例用法：</b></p>
 * <pre>
 *     ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider();
 *     Set&lt;BeanDefinition&gt; components = scanner.findCandidateComponents("com.example.service");
 * </pre>
 *
 * @author zhenghong
 * @date 2025/6/13
 */
public class ClassPathScanningCandidateComponentProvider {

    /**
     * 扫描指定包路径下，所有被 {@link Component} 注解标注的类，并将其封装为 {@link BeanDefinition}。
     *
     * @param basePackage 基础包路径，例如 "com.example.service"
     * @return 所有候选组件的 BeanDefinition 集合（去重且保持插入顺序）
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        // 使用 Hutool 扫描所有被 @Component 注解的类
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            candidates.add(beanDefinition);
        }
        return candidates;
    }

}
