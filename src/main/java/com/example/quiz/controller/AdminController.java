package com.example.quiz.controller;

import com.example.quiz.domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin-home")
    public String adminHome(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return "redirect:/unauthorized";
        }
        return "admin-home";
    }
}
