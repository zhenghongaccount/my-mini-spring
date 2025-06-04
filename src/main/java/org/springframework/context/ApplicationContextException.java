package org.springframework.context;

import org.springframework.beans.BeansException;

/**
 * @author zhenghong
 * @date 2025/6/3
 */
public class ApplicationContextException extends BeansException {

    public ApplicationContextException(String msg) {
        super(msg);
    }

    public ApplicationContextException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
