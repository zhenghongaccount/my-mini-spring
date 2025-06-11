package org.springframework.test.service;

/**
 * @author zhenghong
 * @date 2025/6/8
 */
public class WorldServiceImpl implements WorldService {

    @Override
    public void explode() {
        System.out.println("The Earth is going to explode");
    }
}
