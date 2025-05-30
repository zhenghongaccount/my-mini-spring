package org.springframework.beans.factory;

/**
 * 自定义bean异常
 *
 * @author zhenghong
 * @date 2025/5/31
 */
public class BeansException extends RuntimeException {
    public BeansException(String message) {
        super(message);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }
}
