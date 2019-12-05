package com.csci430.website.security;

import com.csci430.website.repository.ImageRepository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ImageInterceptor extends HandlerInterceptorAdapter {
    private ImageRepository imageRepository;

    public ImageInterceptor(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        System.out.println("Image Interceptor URI: " + request.getRequestURI());
        return imageRepository.findByUserEmailAndUrl(email, request.getRequestURI()) != null;
    }
}
