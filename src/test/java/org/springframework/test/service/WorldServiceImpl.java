package org.springframework.test.service;

/**
 * @author zhenghong
 * @date 2025/6/8
 */
public class WorldServiceImpl implements WorldService {

    private String name;

    @Override
    public void explode() {
        System.out.println("The " + name + " is going to explode");
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
