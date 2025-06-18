package org.springframework.test.aop;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.service.WorldService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AutoProxyTest {
    @Test
    public void testAutoProxy() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:auto-proxy.xml");

        //获取代理对象
        WorldService worldServiceOne = applicationContext.getBean("worldService", WorldService.class);
        worldServiceOne.explode();
        WorldService worldServiceTwo = applicationContext.getBean("worldService", WorldService.class);
        assertThat(worldServiceOne == worldServiceTwo).isTrue();
    }

    @Test
    public void testPopulateProxyBeanWithPropertyValues() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:populate-proxy-bean-with-property-values.xml");

        //获取代理对象
        WorldService worldService = applicationContext.getBean("worldService", WorldService.class);
        worldService.explode();
        assertThat(worldService.getName()).isEqualTo("earth");
    }
}
