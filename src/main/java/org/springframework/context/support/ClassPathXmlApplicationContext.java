package org.springframework.context.support;

/**
 * ClassPathXmlApplicationContext 是 Spring 中最常用的应用上下文实现之一，
 * 用于从类路径（classpath）下加载 XML 配置文件，构建 Spring 容器。
 * <p>
 * 它继承自 AbstractXmlApplicationContext，提供 getConfigLocations 方法的具体实现，
 * 用于指定要加载的 XML 配置文件路径。
 * <p>
 * 典型用法：
 *     ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
 *
 * @author zhenghong
 * @date 2025/6/4
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{

    private final String[] configLocations;

    /**
     * 构造方法：传入配置文件路径数组并立即刷新容器（即启动容器）。
     *
     * @param configLocations XML 配置文件路径数组
     */
    public ClassPathXmlApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    public ClassPathXmlApplicationContext(String configLocation) {
        this(new String[]{configLocation});
    }

    @Override
    protected String[] getConfigLocations() {
        return this.configLocations;
    }
}
