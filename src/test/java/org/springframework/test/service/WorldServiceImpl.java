package org.springframework.test.service;

/**
 * @author zhenghong
 * @date 2025/6/8
 */
public class WorldServiceImpl implements WorldService {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void explode() {
        System.out.println("The Earth is going to explode");
        System.out.println("name: " + getName());
    }
}
