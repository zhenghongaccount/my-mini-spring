package org.springframework.core.io;

import java.net.MalformedURLException;
import java.net.URL;

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
                if (location.startsWith("/")) {
                    return new FileSystemResource(location.substring(1));
                }
                return new FileSystemResource(location);
            }
        }
    }
}
