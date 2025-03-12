package com.graduation.rbackend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Optional;


/**
 * flowchart TD
 *     A[开始] --> B{请求是否为空}
 *     B -->|Yes| E[直接调用过滤链]
 *     B -->|No| C{是否是 HttpServletRequest}
 *     C -->|No| E
 *     C -->|Yes| D{是否已是 CachingRequestWrapper}
 *     D -->|No| F[转换为 CachingRequestWrapper]
 *     D -->|Yes| G[记录日志并继续]
 *     F --> G
 *     G --> H[调用过滤链]
 *     E --> H
 */
@Slf4j

@RequiredArgsConstructor
public class CachingRequestWrapperFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        if (request == null||response==null) {
            log.warn("ServletRequest或 ServletResponse 为空，无法进行缓存");
            return;
        }
        // 检查是否是 HttpServletRequest
        // 创建一个包装后的请求对象
        // 判断是否已是 CachingRequestWrapper，如果不是则转换
        // 如果发生异常，继续过滤器链（不包装请求）
        if (!(request instanceof HttpServletRequest httpRequest)) {
            log.error("ServletRequest 不是 HttpServletRequest，无法进行缓存");
            chain.doFilter(request, response);
            return;
        }
        try {
            // 创建一个包装后的请求对象
            // 判断是否已是 CachingRequestWrapper，如果不是则转换
            if (!(httpRequest instanceof CachingRequestWrapper)) {
                log.info("🟠 请求正在转换为 CachingRequestWrapper");
                httpRequest = new CachingRequestWrapper(httpRequest); // 只创建一次

            } else {
                log.info("✅ 请求已是 CachingRequestWrapper，无需再次转换");

            }
            chain.doFilter(httpRequest, response);

        } catch (Exception e) {
            log.warn("CachingRequestWrapperFilter error: {}", e.getMessage());
            // 如果发生异常，继续过滤器链（不包装请求）
            chain.doFilter(request, response);
        }
    }
}
