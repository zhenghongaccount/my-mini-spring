package org.springframework.test.bean;

import org.springframework.context.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhenghong
 * @date 2025/6/1
 */
@Component
public class Car {

    @Value("${brand}")
    private String brand;

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                '}';
    }
}
