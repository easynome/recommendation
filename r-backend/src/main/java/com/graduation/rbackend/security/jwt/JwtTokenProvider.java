package com.graduation.rbackend.security.jwt;

import io.jsonwebtoken.*;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Date;

//验证和解析 Token
@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;


    public JwtTokenProvider() {
        this.secretKey=Keys.secretKeyFor(SignatureAlgorithm.HS256);// 使用随机生成的密钥
    }

    // 构造函数，接受 SecretKey 作为参数
    public JwtTokenProvider(SecretKey secretKey) {
        this.secretKey = secretKey;
    }
    // 获取 UserDetails
    public UserDetails getUserDetails(String token) {
        Claims claims = getClaims(token);
        String username = claims.getSubject();
        String role = claims.get("role", String.class);

        return User.builder()
                .username(username)
                .password("")  // 密码不存储在 Token 中
                .roles(role)
                .build();
    }
    // 生成 JWT Token
    public String generateToken(String username, String role) {

        // 设置有效期为1天
        long EXPIRATION_TIME = 86400000;

        String token= Jwts.builder()
                .setSubject(username)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
        log.info("✅ 生成的 Token: {}", token);  // 添加调试日志
        return token;
    }

    // 验证 Token 是否有效
    public boolean validateToken(String token) {
        log.info("🟠 validateToken() 被调用，Token: {}", token);
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            log.info("✅ Token 验证成功");
            return true;
        } catch (ExpiredJwtException e) {
            log.error("❌ Token 已过期: {}", e.getMessage());
            return false;  // ✅ 返回 false，避免异常冒泡
        } catch (MalformedJwtException e) {
            log.error("❌ Token 格式错误: {}", e.getMessage());
            return false;
        } catch (SignatureException e) {
            log.error("❌ Token 签名无效: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("❌ Token 验证失败: {}", e.getMessage());
            return false;
        }

    }

    // 从 Token 中获取 Claims
    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    // 从 Token 中获取认证信息
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        String role = claims.get("role", String.class);

        log.info("🟠 Token 解析出的角色 (原始): {}", role);

        UserDetails userDetails = User.builder()
                .username(username)
                .password("")// 密码不存储在 Token 中
                .roles(role.replace("ROLE_", ""))
                .build();
//        List<SimpleGrantedAuthority> authorities= Arrays.stream(role.split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, "",
//                authorities);
        log.info("✅ Token 解析出的角色: {}", role);
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }
}

    // 从 Token 中获取用户名
//    public String getUsernameFromToken(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(JWT_SECRET)
//                .parseClaimsJws(token)
//                .getBody();
//        return claims.getSubject();
//    }



