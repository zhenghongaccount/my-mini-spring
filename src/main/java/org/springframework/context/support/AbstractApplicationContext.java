package org.springframework.context.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * 抽象的应用上下文基类，为具体的 ApplicationContext 实现（如 ClassPathXmlApplicationContext）提供通用模板方法实现。
 * <p>
 * 主要职责包括：
 * <ul>
 *   <li>定义标准的容器刷新过程（refresh 方法），包括 BeanFactory 的创建、BeanDefinition 的加载、后处理器的执行、单例 Bean 的预实例化等</li>
 *   <li>集成资源加载功能，继承自 {@link org.springframework.core.io.DefaultResourceLoader}，支持 classpath、文件系统等资源前缀解析</li>
 *   <li>实现 {@link org.springframework.context.ConfigurableApplicationContext} 接口，支持上下文的可配置行为</li>
 * </ul>
 * 此类采用模板方法模式，部分步骤（如 refreshBeanFactory、getBeanFactory）交由子类实现，以实现对不同容器实现的支持。
 *
 * @author zhenghong
 * @date 2025/6/3
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    public static final String CONVERSION_SERVICE_BEAN_NAME = "conversionService";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        // 创建 BeanFactory，并加载 BeanDefinition
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 添加 ApplicationContextAwareProcessor，让继承自 ApplicationContextAware 的 bean 能感知 bean
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 在 bean 实例化之前，执行 BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // BeanPostProcessor 需要提前与其他 bean 实例化之前注册
        registerBeanPostProcessors(beanFactory);

        // 初始化事件发布者
        initApplicationEventMulticaster();

        // 注册事件监听器
        registerListeners();

        // 设置类型转换服务中心和提前实例化单例 bean
        finishBeanFactoryInitialization(beanFactory);

        // 发布容器刷新完成事件
        finishRefresh();
    }

    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        if (beanFactory.containsBean(CONVERSION_SERVICE_BEAN_NAME)) {
            Object bean = beanFactory.getBean(CONVERSION_SERVICE_BEAN_NAME);
            if (bean instanceof ConversionService) {
                beanFactory.setConversionService((ConversionService) bean);
            }
        }
        // 提前实例化单例 bean
        beanFactory.preInstantiateSingletons();
    }

    @Override
    public boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }

    /**
     * 初始化事件发布者
     */
    protected void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.addSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    /**
     * 注册事件监听器
     */
    protected void registerListeners() {
        Collection<?> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (Object applicationListener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener((ApplicationListener<?>) applicationListener);
        }
    }

    /**
     * 发布容器刷新完成事件
     */
    protected void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    @Override
    public void close() throws BeansException {
        doClose();
    }

    @Override
    public void registerShutdownHook() {
        Thread shutdownHook = new Thread() {
            public void run() {
                doClose();
            }
        };
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    protected void doClose() {
        //发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        destroyBeans();
    }

    protected void destroyBeans() {
        getBeanFactory().destroySingletons();
    }

    /**
     * 注册 BeanPostProcessor
     *
     * @param beanFactory beanFactory
     */
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    /**
     * 创建 BeanFactory，并加载 BeanDefinition
     *
     * @throws BeansException BeansException
     */
    protected abstract void refreshBeanFactory() throws BeansException;


    /**
     * 在 bean 实例化之前，执行 BeanFactoryPostProcessor
     * @param beanFactory 可配置的 BeanFactory，用于获取并传递给每个 BeanFactoryPostProcessor 执行处理
     */
    void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor processor : beanFactoryPostProcessorMap.values()) {
            processor.postProcessBeanFactory(beanFactory);
        }
    }


    public abstract ConfigurableListableBeanFactory getBeanFactory();

}
