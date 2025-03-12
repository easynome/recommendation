package com.graduation.rbackend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain chain)
            throws ServletException, IOException {
//
//        // 检查 Content-Type 是否为 application/json
//        String contentType = request.getContentType();
//        if (contentType == null || !contentType.equalsIgnoreCase("application/json")) {
//            log.warn("❗ Content-Type 不为 application/json，可能导致数据解析失败");
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().write("{\"error\": \"Invalid Content-Type\"}");
//            return;
//        }

//         直接使用传入的 requestWrapper，避免重复创建
//        CachingRequestWrapper requestWrapper = (CachingRequestWrapper) request;
//
//        // 🔎 打印原始 JSON 数据 (仅在 debug 模式下)
//        byte[] bodyBytes = requestWrapper.getInputStream().readAllBytes();
//        if (bodyBytes.length > 0) {
//            String body = new String(bodyBytes, StandardCharsets.UTF_8).trim();
//            log.info("🟠 JwtAuthFilter 中的原始 JSON 数据: {}", body);
//        } else {
//            log.warn("❗ JwtAuthFilter 未接收到 JSON 数据，可能请求体为空或已被其他过滤器消耗");
//        }

        try {
            String token = resolveToken(request);

            log.info("🟠 接收到的原始请求路径: {}", request.getRequestURI());

            if (StringUtils.hasText(token) &&
                    jwtTokenProvider.validateToken(token)) {
                log.info("✅ 解析到 Token: {}", token);
                Authentication authentication =
                        jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("🟠 解析出的角色: {}", authentication.getAuthorities());
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("JwtAuthFilter error: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Invalid JWT token\"}");
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}