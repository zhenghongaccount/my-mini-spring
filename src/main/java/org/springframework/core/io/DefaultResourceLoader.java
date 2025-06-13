package org.springframework.core.io;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 默认的资源加载器实现类，用于根据不同的资源路径加载相应的资源。
 * <p>
 * 支持以下类型的资源路径：
 * <ul>
 *   <li>classpath: 前缀 - 表示从类路径加载资源</li>
 *   <li>URL格式路径 - 表示从网络或文件系统加载资源</li>
 *   <li>普通文件路径 - 表示从文件系统加载资源</li>
 * </ul>
 *
 * <p>使用示例：
 * <pre>
 * ResourceLoader loader = new DefaultResourceLoader();
 *
 * // 从类路径加载
 * Resource classpathResource = loader.getResource("classpath:config.properties");
 *
 * // 从URL加载
 * Resource urlResource = loader.getResource("file:///path/to/file.txt");
 *
 * // 从文件系统加载
 * Resource fileResource = loader.getResource("/path/to/file.txt");
 * </pre>
 *
 * @see ResourceLoader
 *
 * @author zhenghong
 * @date 2025/6/13
 */
public class DefaultResourceLoader implements ResourceLoader {

    public static String CLASSPATH_URL_PREFIX = "classpath:";

    @Override
    public Resource getResource(String location) {
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            String path = location.substring(CLASSPATH_URL_PREFIX.length());
            return new ClassPathResource(path);
        } else {
            try {
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                return new FileSystemResource(location);
            }
        }
    }
}
