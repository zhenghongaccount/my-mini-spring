package org.springframework.test.bean;

import org.springframework.stereotype.Component;

/**
 * @author zhenghong
 * @date 2025/6/1
 */
@Component
public class Car {

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
