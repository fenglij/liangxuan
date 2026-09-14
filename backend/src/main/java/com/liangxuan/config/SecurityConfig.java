package com.liangxuan.config;

import com.liangxuan.auth.service.JwtService;
import com.liangxuan.common.BizException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {
    private static final Set<String> PUBLIC_PATHS = Set.of("/api/auth/login", "/api/auth/register");

    private final JwtService jwtService;
    private final TraceIdInterceptor traceIdInterceptor;
    @Value("${liangxuan.cors.allowed-origin}")
    private String allowedOrigin;

    public SecurityConfig(JwtService jwtService, TraceIdInterceptor traceIdInterceptor) {
        this.jwtService = jwtService;
        this.traceIdInterceptor = traceIdInterceptor;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    HandlerInterceptor jwtInterceptor() {
        return new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                    return true;
                }
                if (PUBLIC_PATHS.contains(request.getServletPath()) && "POST".equalsIgnoreCase(request.getMethod())) {
                    return true;
                }
                if (request.getServletPath().equals("/api/companies")
                        && "GET".equalsIgnoreCase(request.getMethod())) {
                    return true;
                }
                if (request.getServletPath().matches("/api/companies/\\d+")
                        && "GET".equalsIgnoreCase(request.getMethod())) {
                    return true;
                }
                String authorization = request.getHeader("Authorization");
                if (authorization == null || !authorization.startsWith("Bearer ")) {
                    throw new BizException(401, "请先登录");
                }
                try {
                    request.setAttribute("userId", jwtService.parseUserId(authorization.substring(7)));
                    return true;
                } catch (RuntimeException exception) {
                    throw new BizException(401, "登录状态已失效，请重新登录");
                }
            }
        };
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(traceIdInterceptor).addPathPatterns("/api/**");
        registry.addInterceptor(jwtInterceptor()).addPathPatterns("/api/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins(allowedOrigin)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
