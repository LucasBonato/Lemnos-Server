package com.lemnos.server.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "http://localhost:5173/**", "http://localhost:5173/login/**", "https://lemnos.vercel.app", "https://lemnos.vercel.app/**")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}