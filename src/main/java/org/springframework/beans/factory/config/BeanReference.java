package org.springframework.beans.factory.config;

/**
 * 一个 Bean 对另一个 Bean 的引用
 *
 * @author zhenghong
 * @date 2025/6/1
 */
public class BeanReference {

    private final String BeanName;

    public BeanReference(String beanName) {
        BeanName = beanName;
    }

    public String getBeanName() {
        return BeanName;
    }
}
