package com.mithila.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration
{
    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8080", "http://localhost:8083"
                                ,"http://40.117.143.140:8080","https://40.117.143.140:8443",
                                "https://couchlane.eastus.cloudapp.azure.com:8443",
                                "http://couchlane.eastus.cloudapp.azure.com:8080")
                        .allowCredentials(true)
                        .allowedMethods("GET" ,"POST" ,"PUT" ,"OPTIONS" ,"DELETE")
                ;
            }
        };
    }
}