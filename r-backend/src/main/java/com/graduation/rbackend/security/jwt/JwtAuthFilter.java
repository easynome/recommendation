package com.graduation.rbackend.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//拦截和处理请求
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain chain)
            throws ServletException, IOException {


        log.info("🟠 请求路径: {}", request.getRequestURI());
        log.info("🟠 请求头 Authorization: {}", request.getHeader("Authorization"));
        String requestPath = request.getRequestURI();

        // ➤ 检查是否是 `/api/auth/**` 请求，直接放行
        if (requestPath.startsWith("/api/auth/")) {
            log.info("🟢 放行 `/api/auth/` 请求: {}", requestPath);
            chain.doFilter(request, response);
            return;  // ➤ 提前结束，避免继续执行 Token 检查
        }

        String token = resolveToken(request);

        log.info("🟠 接收到的原始请求路径: {}", request.getRequestURI());

        // ➤ 判断 Token 是否存在
        if (!StringUtils.hasText(token)) {
            log.warn("❗ 未提供 Token，未授权请求");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Token is missing\"}");
            return;
        }

        // ➤ Token 验证逻辑
        if (!jwtTokenProvider.validateToken(token)) {
            log.warn("❗ Token 无效，未授权请求");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Invalid JWT token\"}");
            return;
        }

        // ➤ 设置 Authentication
        log.info("✅ 解析到 Token: {}", token);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("🟠 解析出的角色: {}", authentication.getAuthorities());
        } else {
            log.warn("❗ 未找到 Authentication 对象，未设置 SecurityContext");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Invalid JWT token\"}");
            SecurityContextHolder.getContext().setAuthentication(null);
            return;
        }

        chain.doFilter(request, response);
    }
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.info("🟠 resolveToken() 接收到的 Authorization 请求头: {}", bearerToken);
        if (StringUtils.hasText(bearerToken)) {
            if (bearerToken.startsWith("Bearer ")) {
                log.info("✅ Token 解析成功 (带有 Bearer 前缀)");
                return bearerToken.substring(7);
            } else {
                log.info("✅ Token 解析成功 (无 Bearer 前缀)");
                return bearerToken;
            }
        }
        log.warn("❗ Token 未找到或为空");
        return null;
    }
}