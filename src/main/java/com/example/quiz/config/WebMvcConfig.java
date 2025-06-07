package com.example.quiz.config;

import com.example.quiz.interceptor.AdminAccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AdminAccessInterceptor adminAccessInterceptor;

    @Autowired
    public WebMvcConfig(AdminAccessInterceptor adminAccessInterceptor) {
        this.adminAccessInterceptor = adminAccessInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminAccessInterceptor)
                .addPathPatterns("/admin/**");
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

}
