package org.springframework.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 资源抽象类，仅实现了exists方法
 *
 * @author zhenghong
 * @date 2025/6/1
 */
public abstract class AbstractResource implements Resource {
    @Override
    public boolean exists() {
        try {
            return getFile().exists();
        } catch (Exception e) {
            try {
                InputStream inputStream = getInputStream();
                inputStream.close();
                return true;
            } catch (Exception isEx) {
                return false;
            }
        }
    }

    @Override
    public File getFile() throws IOException {
        throw new FileNotFoundException("resource cannot be resolved to absolute file path");
    }

    @Override
    public URL getURL() throws IOException {
        throw new FileNotFoundException("resource cannot be resolved to URL");
    }
}
