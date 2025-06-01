package org.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Resource的一个实现类，用于处理例如网页、图片或任何通过网络可访问的资源,通过url.openConnection.getInputStream获取资源
 *
 * @author zhenghong
 * @date 2025/6/1
 */
public class UrlResource implements Resource{

    private final URL url;

    public UrlResource(URL url) {
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection urlConnection = this.url.openConnection();
        return urlConnection.getInputStream();
    }
}
