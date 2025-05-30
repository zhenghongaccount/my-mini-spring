package org.springframework;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class BeanFactoryTest {
    @Test
    public void testBeanFactory() throws Exception{
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.registerBean("HelloService", new HelloService());
        HelloService helloService = (HelloService)beanFactory.getBean("HelloService");
        assertThat(helloService).isNotNull();
        assertThat(helloService.sayHello()).isEqualTo("Hello");
    }

    class HelloService {
        public String sayHello() {
            System.out.println("Hello");
            return "Hello";
        }
    }
}
