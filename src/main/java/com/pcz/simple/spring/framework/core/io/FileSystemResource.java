package com.pcz.simple.spring.framework.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件系统资源
 *
 * @author picongzhi
 */
public class FileSystemResource implements Resource {
    /**
     * 文件
     */
    private final File file;

    /**
     * 路径
     */
    private final String path;

    public FileSystemResource(File file) {
        this.file = file;
        this.path = file.getPath();
    }

    public FileSystemResource(String path) {
        this.file = new File(path);
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    /**
     * 获取文件路径
     *
     * @return 文件路径
     */
    public final String getPath() {
        return this.path;
    }
}
