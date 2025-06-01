package org.springframework.core.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Resource的实现类，仅处理文件资源
 *
 * @author zhenghong
 * @date 2025/6/1
 */
public class FileSystemResource implements Resource{

    private final String filePath;

    public FileSystemResource(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        try {
            Path path = new File(this.filePath).toPath();
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
