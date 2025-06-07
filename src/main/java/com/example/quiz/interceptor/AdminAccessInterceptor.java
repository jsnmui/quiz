package com.example.quiz.interceptor;

import com.example.quiz.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class AdminAccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
            return false;
        }

        User user = (User) session.getAttribute("user");
        if (!user.isAdmin()) {
            response.sendRedirect("/home");
            return false;
        }

        return true;
    }
}
