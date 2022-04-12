package com.example.paymentservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PaymentServiceConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:4200", "http://renting-vehicles-app.s3-website-us-east-1.amazonaws.com", "http://localhost:1027").allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
