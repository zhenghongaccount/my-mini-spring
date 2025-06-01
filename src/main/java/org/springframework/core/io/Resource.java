package org.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源接口,仅有一个getInputStream方法
 *
 * @author zhenghong
 * @date 2025/6/1
 */
public interface Resource {
    InputStream getInputStream() throws IOException;
}
