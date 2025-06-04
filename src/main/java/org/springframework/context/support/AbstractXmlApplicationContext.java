package org.springframework.context.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * AbstractXmlApplicationContext 是 Spring 中基于 XML 配置文件加载 Bean 定义的抽象应用上下文。
 * <p>
 * 作为抽象类，它继承自 AbstractRefreshableApplicationContext，
 * 提供了基于 XML 的 BeanDefinition 加载逻辑（使用 XmlBeanDefinitionReader 实现）。
 * <p>
 * 子类需要实现 getConfigLocations() 方法，用于指定 XML 配置文件路径。
 * 常见子类包括 ClassPathXmlApplicationContext、FileSystemXmlApplicationContext 等。
 *
 * @author zhenghong
 * @date 2025/6/4
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    /**
     * 抽象方法：由子类实现，用于提供 XML 配置文件的位置。
     *
     * @return 配置文件路径数组，路径格式如："classpath:beans.xml" 或 "file:/path/to/config.xml"
     */
    protected abstract String[] getConfigLocations();

}
