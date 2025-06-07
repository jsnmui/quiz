package com.example.quiz.controller;

import com.example.quiz.domain.User;
import com.example.quiz.domain.QuizResult;
import com.example.quiz.service.QuizResultService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ResultManagementController {

    private final QuizResultService quizResultService;

    @GetMapping("/results")
    public String viewResults(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(required = false) Integer categoryId,
                              @RequestParam(required = false) Integer userId,
                              @RequestParam(required = false, defaultValue = "") String reset,
                              Model model, HttpSession session) {

        if (!isAdmin(session)) return "redirect:/unauthorized";

        // Reset filters if requested
        if (reset.equals("true")) {
            categoryId = null;
            userId = null;
        }

        int pageSize = 5;
        int offset = (page - 1) * pageSize;

        List<QuizResult> results = quizResultService.getQuizResults(offset, pageSize, categoryId, userId);
        int total = quizResultService.countQuizResults(categoryId, userId);
        int totalPages = (int) Math.ceil((double) total / pageSize);

        model.addAttribute("results", results);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("userId", userId);

        return "admin-quiz-results";
    }


    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && user.isAdmin();
    }


}
