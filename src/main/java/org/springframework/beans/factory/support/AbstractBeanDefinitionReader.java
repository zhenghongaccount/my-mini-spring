package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

/**
 * 抽象的 BeanDefinitionReader 实现类，提供读取器的基本功能实现。
 * 主要职责包括：
 * - 持有 BeanDefinitionRegistry，用于注册 bean 定义信息
 * - 持有 ResourceLoader，用于加载配置文件资源
 * - 提供公共的加载方法（如支持批量加载）
 * 子类只需专注于具体格式（如 XML、Properties 等）bean 配置的解析逻辑。
 *
 * @author zhenghong
 * @date 2025/6/1
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    @Override
    public void loadBeanDefinitions(String[] locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry,new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
