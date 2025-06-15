package org.springframework.context.annotation;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 类路径 Bean 定义扫描器。
 * <p>
 * 该类负责扫描指定包路径下标注了 {@code @Component} 注解的类，
 * 并将其转换为 {@link BeanDefinition} 对象，注册到 Bean 容器中。
 * </p>
 * <p>
 * 支持解析 Bean 的作用域（如 {@code @Scope("prototype")}）并设置到定义中。
 * </p>
 *
 * <p>常用于仿 Spring 框架中的自动注入与组件扫描机制。</p>
 *
 * 使用示例：
 * <pre>
 * BeanDefinitionRegistry registry = ...;
 * ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
 * scanner.doScan("com.example.service");
 * </pre>
 *
 * @author zhenghong
 * @date 2025/6/14
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private final BeanDefinitionRegistry registry;

    public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME = "org.springframework.context.annotation.internalAutowiredAnnotationProcessor";

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 执行扫描过程。
     * <p>扫描指定的基础包路径，查找带有 {@code @Component} 注解的类，解析其作用域、Bean 名称并注册到容器中。</p>
     *
     * @param basePackages 需要扫描的包路径（可以多个）
     */
    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                String s = resolveBeanScope(candidate);
                if (StrUtil.isNotEmpty(s)) {
                    candidate.setScope(s);
                }
                String beanName = determineBeanName(candidate);
                registry.registerBeanDefinition(beanName, candidate);
            }
        }
        registry.registerBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME, new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    /**
     * 解析 Bean 的作用域注解（@Scope）。
     *
     * @param beanDefinition Bean 定义
     * @return Bean 的作用域（如 "singleton"、"prototype"），如果未声明则返回空字符串
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (scope != null) {
            return scope.value();
        }
        return StrUtil.EMPTY;
    }

    /**
     * 确定 Bean 的名称。
     * <p>优先使用 @Component 注解中的 value 值，否则使用类名首字母小写作为名称。</p>
     *
     * @param beanDefinition Bean 定义
     * @return Bean 名称
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            return StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }
}
