package com.example.quiz.controller;

import com.example.quiz.domain.Category;
import com.example.quiz.domain.User;
import com.example.quiz.domain.QuizResult;
import com.example.quiz.service.QuestionService;
import com.example.quiz.service.QuizResultService;
import com.example.quiz.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ResultManagementController {

    private final QuizResultService quizResultService;
    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/results")
    public String viewResults(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(required = false) String categoryName,
                              @RequestParam(required = false) String userName,
                              @RequestParam(required = false) boolean reset,
                              Model model, HttpSession session) {

        if (!isAdmin(session)) return "redirect:/unauthorized";

        int pageSize = 5;
        int offset = (page - 1) * pageSize;

        // Reset filters if requested
        if (reset) {
            categoryName = null;
            userName = null;
        }

        List<QuizResult> results = quizResultService.getQuizResults(offset, pageSize, categoryName, userName);
        int total = quizResultService.countQuizResults(categoryName, userName);
        int totalPages = (int) Math.ceil((double) total / pageSize);

        List<Category> categoryList = questionService.getAllCategories();
        List<User> userList = userService.getAllUsers();
        Set<String> userNameSet = new HashSet<>();

        for (User user : userList) {
            userNameSet.add(user.getFirstname() + " " + user.getLastname());

        }

        List <String> userNameList = new ArrayList<>(userNameSet);
        model.addAttribute("results", results);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("userName", userName);

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("userNameList", userNameList);

        return "admin-quiz-results";
    }


    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && user.isAdmin();
    }


}
