package com.example.quiz.config;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.quiz.filter.LoginFilter;

@Configuration
public class FilterConfig {

    @Bean
    public Filter loginFilter() {
        return new LoginFilter();
    }
}