package com.graduation.rbackend.config;

import com.graduation.rbackend.security.jwt.JwtAuthFilter;
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

    private final JwtAuthFilter jwtAuthFilter;

//    private SecurityConfig(UserDetailsServiceImpl userDetailsService){
//        this.userDetailsService = userDetailsService;
//    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()  // 允许所有用户访问认证 API
                .requestMatchers("/api/admins/**").hasRole("ADMIN") // 仅管理员访问
                .requestMatchers("/api/teachers/**").hasRole("TEACHER") // 仅教师访问
                .requestMatchers("/api/students/**").hasRole("STUDENT") // 仅学生访问
                .requestMatchers("/api/courses/**", "/api/recommendations/**").authenticated() // 认证用户访问
                .anyRequest().denyAll()
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}