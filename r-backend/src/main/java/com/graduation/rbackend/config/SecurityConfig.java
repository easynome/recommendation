package com.graduation.rbackend.config;

import com.graduation.rbackend.security.jwt.CachingRequestWrapper;
import com.graduation.rbackend.security.jwt.CachingRequestWrapperFilter;
import com.graduation.rbackend.security.jwt.JwtAuthFilter;
import com.graduation.rbackend.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 配置Spring Security，进行认证和授权
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
//    @Bean
//    public CachingRequestWrapperFilter cachingRequestWrapperFilter() {
//        return new CachingRequestWrapperFilter();
//    }
//    @Bean
//    public JwtAuthFilter jwtAuthFilter() {
//        return new JwtAuthFilter(jwtTokenProvider);
//    }

//    private SecurityConfig(UserDetailsServiceImpl userDetailsService){
//        this.userDetailsService = userDetailsService;
//    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 直接实例化过滤器
        CachingRequestWrapperFilter cachingRequestWrapperFilter = new CachingRequestWrapperFilter();
        JwtAuthFilter jwtAuthFilter = new JwtAuthFilter(jwtTokenProvider);
        http
                .csrf(csrf->csrf.disable())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/auth/**").permitAll()  // 允许所有用户访问认证 API
                        .requestMatchers("/api/admins/**").hasRole("ADMIN") // 仅管理员访问
                        .requestMatchers("/api/teachers/**").hasRole("TEACHER") // 仅教师访问
                        .requestMatchers("/api/students/**").hasRole("STUDENT") // 仅学生访问
                        .requestMatchers("/api/courses/**", "/api/recommendations/**").authenticated() // 认证用户访问
                        .anyRequest().denyAll()
                )
                .addFilterBefore(cachingRequestWrapperFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}