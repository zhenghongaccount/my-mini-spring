package org.springframework.core.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 资源接口，常见资源有URL、File等
 *
 * @author zhenghong
 * @date 2025/6/1
 */
public interface Resource {

    boolean exists();

    File getFile() throws IOException;

    URL getURL() throws IOException;

    InputStream getInputStream() throws IOException;
}
