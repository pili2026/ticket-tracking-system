package com.ticket.tracking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PageConfig implements WebMvcConfigurer {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/dashboard").setViewName("dashboard");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/pm_dashboard").setViewName("pm_dashboard");
        registry.addViewController("/qa_dashboard").setViewName("qa_dashboard");
        registry.addViewController("/rd_dashboard").setViewName("rd_dashboard");
        registry.addViewController("/pm_dashboard/create").setViewName("create");
        registry.addViewController("/qa_dashboard/qa_create").setViewName("qa_create");
    }

}
