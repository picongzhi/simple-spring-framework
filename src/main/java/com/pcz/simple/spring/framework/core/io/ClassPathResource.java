package com.pcz.simple.spring.framework.core.io;

import cn.hutool.core.lang.Assert;
import com.pcz.simple.spring.framework.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * ClassPath 资源
 *
 * @author picongzhi
 */
public class ClassPathResource implements Resource {
    /**
     * 路径
     */
    private final String path;

    /**
     * 类加载器
     */
    private final ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        Assert.notNull(path, "Path must not be null");
        this.path = path;
        this.classLoader = classLoader != null ?
                classLoader :
                ClassUtils.getDefaultClassLoader();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream inputStream = this.classLoader.getResourceAsStream(this.path);
        if (inputStream == null) {
            throw new FileNotFoundException(this.path + " cannot be opened because it does not exist");
        }

        return inputStream;
    }
}
