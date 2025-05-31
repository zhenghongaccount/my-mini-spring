package org.springframework.beans.factory;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class BeanFactoryTest {
    @Test
    public void testBeanFactory() throws Exception{
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
        defaultListableBeanFactory.registerBeanDefinition("helloService", beanDefinition);
        HelloService bean = (HelloService)defaultListableBeanFactory.getBean("helloService");
        bean.sayHello();
    }
}
