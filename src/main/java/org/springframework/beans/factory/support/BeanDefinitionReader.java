package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 从配置中读取 Bean 信息的加载器，结合了加载资源和注册 Bean 信息的能力，即结合了 BeanDefinitionRegistry 和 ResourceLoader
 *
 * @author zhenghong
 * @date 2025/6/1
 */
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String [] locations) throws BeansException;
}
