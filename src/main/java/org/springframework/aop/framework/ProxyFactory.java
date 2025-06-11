package org.springframework.aop.framework;

import org.springframework.aop.AdvisedSupport;

/**
 * ProxyFactory 是一个用于创建 AOP（面向切面编程）代理对象的工厂类。
 * 它根据配置（AdvisedSupport）决定使用 JDK 动态代理还是 CGLIB 代理来生成目标对象的代理。
 *
 * @author zhenghong
 * @date 2025/6/11
 */
public class ProxyFactory {

    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public void setAdvisedSupport(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        if (advisedSupport.isProxyTargetClass()) {
            return new CglibAopProxy(advisedSupport);
        }
        return new JdkDynamicAopProxy(advisedSupport);
    }

}
