package org.springframework.test.ioc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.test.bean.Car;
import org.springframework.test.bean.Person;
import org.springframework.test.common.CustomBeanFactoryPostProcessor;
import org.springframework.test.common.CustomBeanPostProcessor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author zhenghong
 * @date 2025/6/3
 */
public class BeanFactoryProcessorAndBeanPostProcessorTest {

    @Test
    public void testBeanFactoryPostProcessor() throws Exception {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        CustomBeanFactoryPostProcessor beanFactoryPostProcessor = new CustomBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        Person person = (Person) beanFactory.getBean("person");
        System.out.println(person);
        //name 属性在 CustomBeanFactoryPostProcessor 中被修改为 John Doe
        assertThat(person.getName()).isEqualTo("John Doe");
    }

    @Test
    public void testCustomBeanPostProcessor() throws Exception {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        new XmlBeanDefinitionReader(beanFactory).loadBeanDefinitions("classpath:spring.xml");

        CustomBeanPostProcessor customBeanPostProcessor = new CustomBeanPostProcessor();
        beanFactory.addBeanPostProcessor(customBeanPostProcessor);
        Car car = (Car) beanFactory.getBean("car");

        System.out.println(car);
        //brand 属性在 CustomerBeanPostProcessor 中被修改为 lamborghini
        assertThat(car.getBrand()).isEqualTo("lamborghini");
    }

}
