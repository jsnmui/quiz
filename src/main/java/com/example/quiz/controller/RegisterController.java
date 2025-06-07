package com.example.quiz.controller;

import com.example.quiz.domain.User;
import com.example.quiz.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("user") User user, HttpServletRequest request) {
        // Mark new users as active non-admins
        user.setActive(true);
        user.setAdmin(false);

        // Save the user
        userService.createNewUser(user);

        // Clear session and redirect to login
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) oldSession.invalidate();

        return "redirect:/login";
    }
}
