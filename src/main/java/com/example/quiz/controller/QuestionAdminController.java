package com.example.quiz.controller;

import com.example.quiz.domain.Question;
import com.example.quiz.domain.User;

import com.example.quiz.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/questions")
@RequiredArgsConstructor
public class QuestionAdminController {

    private final QuestionService questionService;

    @GetMapping
    public String showQuestions(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) return "redirect:/unauthorized";

        List<Question> questions = questionService.getAllQuestionsWithChoicesAndCategory();
        model.addAttribute("questions", questions);
        return "admin-question-management";
    }

    @PostMapping("/toggle-status")
    public String toggleStatus(@RequestParam int questionId, HttpSession session) {
        if (!((User) session.getAttribute("user")).isAdmin()) return "redirect:/unauthorized";
        questionService.toggleStatus(questionId);
        return "redirect:/admin/questions";
    }

    // Add /edit endpoints will follow
}