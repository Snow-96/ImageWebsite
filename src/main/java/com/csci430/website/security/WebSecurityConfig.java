package com.csci430.website.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.csci430.website.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {
    /**
     * session_key
     */
    public final static String SESSION_KEY = "user";

    /**
     * JPA bean
     */
    private final ImageRepository imageRepository;

    @Value("${imagesPath}")
    private String imagesPath;
    @Value("${imagesDir}")
    private String imagesDir;

    @Autowired
    public WebSecurityConfig(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration globalInterceptor = registry.addInterceptor(new GlobalInterceptor());
        InterceptorRegistration imagesInterceptor = registry.addInterceptor(new ImageInterceptor(imageRepository));

        globalInterceptor.addPathPatterns("/**");
        globalInterceptor.excludePathPatterns("/login**");
        globalInterceptor.excludePathPatterns("/register**");
        globalInterceptor.excludePathPatterns("/js/**");
        globalInterceptor.excludePathPatterns("/images/**");

        imagesInterceptor.addPathPatterns("/images/PIC**");
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(imagesDir + "**").addResourceLocations("file://" + imagesPath);
    }
}