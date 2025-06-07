package com.example.quiz.controller;

import com.example.quiz.domain.*;
import com.example.quiz.service.QuizService;
import com.example.quiz.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class ResultsController {

    private final QuizService quizService;
    private final QuestionService questionService;

    @GetMapping("/results/{quizId}")
    public String viewResult(@PathVariable int quizId, HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Quiz quiz = quizService.getQuizById(quizId);

        //  Allow if user is admin or it's their own quiz
        if (!user.isAdmin() && quiz.getUserId() != user.getUserId()) {
            return "unauthorized";
        }

        List<Question> questions = questionService.getQuestionsByQuizId(quizId);
        List<QuestionResultDto> results = new ArrayList<>();
        int score = 0;

        for (Question q : questions) {

            System.out.println("🔍 quizId = " + quizId + ", questionId = " + q.getQuestionId());
            int selectedId = questionService.getSelectedChoiceId(quizId, q.getQuestionId());

            System.out.println("👉 Selected Choice ID = " + selectedId);


            int correctId = q.getChoices().stream()
                    .filter(Choice::isCorrect)
                    .findFirst()
                    .map(Choice::getChoiceId)
                    .orElse(-1);

            if (selectedId == correctId) {
                score++;
            }
            System.out.println("Selected: " + selectedId + "quizid: " + quizId + "correcId" + correctId );
            results.add(new QuestionResultDto(q, selectedId, correctId));
        }

        model.addAttribute("quiz", quiz);
        model.addAttribute("user", user);
        model.addAttribute("questionResults", results);
        model.addAttribute("score", score);
        model.addAttribute("total", results.size());
        model.addAttribute("startTime", quiz.getTimeStart());
        model.addAttribute("endTime", quiz.getTimeEnd());

        return "quiz-result";
    }
}
