package org.springframework.test.ioc;

import org.junit.jupiter.api.Test;
import org.springframework.test.bean.Car;
import org.springframework.test.bean.Person;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.DefaultResourceLoader;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author zhenghong
 * @date 2025/6/1
 */
public class XmlFileDefineBeanTest {

    @Test
    public void testXmlFile() throws Exception {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, new DefaultResourceLoader());
        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        Person person = (Person)beanFactory.getBean("person");
        System.out.println(person);
        assertThat(person.getName()).isEqualTo("Jeron");
        assertThat(person.getCar().getBrand()).isEqualTo("porsche");

        Car car = (Car) beanFactory.getBean("car");
        System.out.println(car);
        assertThat(car.getBrand()).isEqualTo("porsche");
    }

}
