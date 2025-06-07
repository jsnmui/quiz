package com.example.quiz.controller;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;
import com.example.quiz.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/questions")
public class QuestionManagementController {

    private final QuestionService questionService;

    @GetMapping("/edit")
    public String editQuestionForm(@RequestParam("questionId") int questionId, Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/unauthorized";

        Question question = questionService.getQuestionById(questionId);
        List<Choice> choices = questionService.getChoicesForQuestion(questionId);

        model.addAttribute("question", question);
        model.addAttribute("choices", choices);

        return "question-edit"; // JSP page name
    }

    @PostMapping("/edit")
    public String submitEditQuestion(@RequestParam int questionId,
                                     @RequestParam String description,
                                     @RequestParam("choice") List<String> choices,
                                     @RequestParam("correctChoice") int correctChoiceId,
                                     HttpSession session) {

        if (!isAdmin(session)) return "redirect:/unauthorized";

        questionService.updateQuestionWithChoices(questionId, description, choices, correctChoiceId);
        return "redirect:/admin/questions";
    }

    @GetMapping("/add")
    public String addQuestionForm(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/unauthorized";

        model.addAttribute("categories", questionService.getAllCategories());
        return "question-add";
    }

    @PostMapping("/add")
    public String submitNewQuestion(@RequestParam int categoryId,
                                    @RequestParam String description,
                                    @RequestParam("choice") List<String> choices,
                                    @RequestParam("correctChoice") int correctChoiceIndex,
                                    HttpSession session) {

        if (!isAdmin(session)) return "redirect:/unauthorized";

        questionService.createQuestionWithChoices(categoryId, description, choices, correctChoiceIndex);
        return "redirect:/admin/questions";
    }




    private boolean isAdmin(HttpSession session) {
        var user = session.getAttribute("user");
        return user != null && ((com.example.quiz.domain.User) user).isAdmin();
    }
}