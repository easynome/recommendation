package com.graduation.rbackend.security.jwt;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Getter
@Slf4j
public class CachingRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] cachedBody;

    public CachingRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        cachedBody=request.getInputStream().readAllBytes();
        // 添加调试日志
        if (cachedBody == null || cachedBody.length == 0) {
            log.warn("❗ StreamUtils.copyToByteArray() 复制的请求体为空！");
        } else {
            log.info("✅ 请求体成功缓存: {}", new String(cachedBody, StandardCharsets.UTF_8));
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        return new CachedServletInputStream(cachedBody);
    }

    @Override
    public BufferedReader getReader(){
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    private static class CachedServletInputStream extends ServletInputStream {

        private final ByteArrayInputStream inputStream;

        public CachedServletInputStream(byte[] cachedBody) {
            this.inputStream = new ByteArrayInputStream(cachedBody);
        }

        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            // Not required for caching
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }
    }
}
