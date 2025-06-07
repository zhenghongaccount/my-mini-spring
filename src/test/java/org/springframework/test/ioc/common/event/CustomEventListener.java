package org.springframework.test.ioc.common.event;

import org.springframework.context.ApplicationListener;

/**
 * @author zhenghong
 * @date 2025/6/7
 */
public class CustomEventListener implements ApplicationListener<CustomEvent> {
    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println(this.getClass().getName());
    }
}
