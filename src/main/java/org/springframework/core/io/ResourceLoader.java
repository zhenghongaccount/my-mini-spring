package org.springframework.core.io;

/**
 * 资源加载器
 *
 * @author zhenghong
 * @date 2025/6/1
 */
public interface ResourceLoader {
    Resource getResource(String location);
}
