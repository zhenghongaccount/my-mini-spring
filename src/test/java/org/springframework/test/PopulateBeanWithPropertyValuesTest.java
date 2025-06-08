package org.springframework.test;

import org.junit.jupiter.api.Test;
import org.springframework.test.bean.Car;
import org.springframework.test.bean.Person;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author zhenghong
 * @date 2025/6/1
 */
public class PopulateBeanWithPropertyValuesTest {

    @Test
    public void testPopulateBeanWithPropertyValues() throws Exception {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name", "Jerron"));
        propertyValues.addPropertyValue(new PropertyValue("age", 23));
        BeanDefinition beanDefinition = new BeanDefinition(Person.class, propertyValues);
        beanFactory.registerBeanDefinition("person", beanDefinition);

        Person person = (Person) beanFactory.getBean("person");
        System.out.println(person);
        assertThat(person.getName()).isEqualTo("derek");
        assertThat(person.getAge()).isEqualTo(18);
    }

    @Test
    public void testPopulateBeanWithBean() throws Exception {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //注册Car
        PropertyValues propertyValuesForCar = new PropertyValues();
        propertyValuesForCar.addPropertyValue(new PropertyValue("brand", "porsche"));
        BeanDefinition carBeanDefinition = new BeanDefinition(Car.class, propertyValuesForCar);
        beanFactory.registerBeanDefinition("car", carBeanDefinition);

        //注册Person
        PropertyValues propertyValuesForPerson = new PropertyValues();
        propertyValuesForPerson.addPropertyValue(new PropertyValue("name","Jerron"));
        propertyValuesForPerson.addPropertyValue(new PropertyValue("age", 23));

        //Person依赖Car
        propertyValuesForPerson.addPropertyValue(new PropertyValue("car",new BeanReference("car")));
        BeanDefinition personBeanDefinition = new BeanDefinition(Person.class, propertyValuesForPerson);
        beanFactory.registerBeanDefinition("person",personBeanDefinition);

        Person person = (Person) beanFactory.getBean("person");
        System.out.println(person);
        assertThat(person.getName()).isEqualTo("Jerron");
        assertThat(person.getAge()).isEqualTo(23);
        Car car = person.getCar();
        System.out.println(car);
        assertThat(car).isNotNull();
        assertThat(car.getBrand()).isEqualTo("porsche");

    }
}
