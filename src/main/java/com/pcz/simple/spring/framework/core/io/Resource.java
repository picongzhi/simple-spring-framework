package com.pcz.simple.spring.framework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源
 *
 * @author picongzhi
 */
public interface Resource {
    /**
     * 获取 InputStream
     *
     * @return InputStream
     * @throws IOException IO异常
     * @see InputStream
     */
    InputStream getInputStream() throws IOException;
}
