package com.example.quiz.controller;


import com.example.quiz.domain.Question;
import com.example.quiz.domain.User;
import com.example.quiz.service.QuestionService;
import com.example.quiz.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserManagementController {

    private final UserService userService;
    private final QuestionService questionService;

    @GetMapping("/user-management")
    public String showUserList(@RequestParam(defaultValue = "1") int page, Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/unauthorized";

        int pageSize = 5;
        int validPage = Math.max(1, page);
        List<User> users = userService.getUsersByPage(validPage, pageSize); // pass page number (not offset)
        int totalUsers = userService.countAllUsers();
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        model.addAttribute("users", users);
        model.addAttribute("currentPage", validPage);
        model.addAttribute("totalPages", totalPages);

        return "admin-user-management";
    }

    @PostMapping("/user-management/activate")
    public String activateUser(@RequestParam int userId, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/unauthorized";
        userService.setUserStatus(userId, true);
        return "redirect:/admin/user-management";
    }

    @PostMapping("/user-management/suspend")
    public String suspendUser(@RequestParam int userId, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/unauthorized";
        userService.setUserStatus(userId, false);
        return "redirect:/admin/user-management";
    }



    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && user.isAdmin();
    }
}
