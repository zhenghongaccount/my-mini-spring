package org.springframework.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * AbstractResource的一个实现类，仅处理路径资源，使用ClassPathResource.class.getClassLoader()加载资源
 *
 * @author zhenghong
 * @date 2025/6/1
 */
public class ClassPathResource extends AbstractResource {

    private final String path;

    ClassLoader classLoader;

    public ClassPathResource(String path) {
        this.path = path;
        this.classLoader = ClassPathResource.class.getClassLoader();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        try(InputStream resourceAsStream = this.classLoader.getResourceAsStream(path)) {
            if (resourceAsStream == null) {
                throw new FileNotFoundException("resource cannot be opened because it does not exist");
            }
            return resourceAsStream;
        }
    }

    @Override
    public URL getURL() throws IOException {
        URL url = classLoader.getResource(path);
        if (url == null) {
            throw new FileNotFoundException("resource cannot be resolved to URL because it does not exist");
        }
        return url;
    }

    public String getPath() {
        return path;
    }
}
