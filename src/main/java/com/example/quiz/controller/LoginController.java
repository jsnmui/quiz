package com.example.quiz.controller;

import com.example.quiz.domain.User;
import com.example.quiz.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String getLogin(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

//         redirect to /home if user is already logged in
        if (session != null && session.getAttribute("user") != null) {
            return "redirect:/home";
        }

        return "login";
    }

    // validate that we are always getting a new session after login
    @PostMapping("/login")
    public String postLogin(@RequestParam String username,
                            @RequestParam String password,
                            HttpServletRequest request, Model model) {

        Optional<User> possibleUser = loginService.validateLogin(username, password);


        if (possibleUser.isPresent()) {
            User user = possibleUser.get();

            if (user.isActive()) {
                // Login success
                HttpSession oldSession = request.getSession(false);
                if (oldSession != null) oldSession.invalidate();

                HttpSession newSession = request.getSession(true);
                newSession.setAttribute("user", user);

                return user.isAdmin() ? "redirect:/admin-home" : "redirect:/home";
            } else {
                // User is suspended
                model.addAttribute("error", "Your account is suspended. Please contact support.");
                return "login";
            }
        } else {
            // Invalid username or password
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        HttpSession oldSession = request.getSession(false);
        // invalidate old session if it exists
        if(oldSession != null) oldSession.invalidate();
        return "login";
    }

    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }
}
