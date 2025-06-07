package com.example.quiz.controller;

import com.example.quiz.domain.Category;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.Quiz;
import com.example.quiz.domain.User;
import com.example.quiz.service.CategoryService;
import com.example.quiz.service.QuestionService;
import com.example.quiz.service.QuizService;
import jakarta.servlet.http.HttpSession;
import lombok.var;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final CategoryService categoryService;
    private final QuizService quizService;
    private final QuestionService questionService;

    public HomeController(CategoryService categoryService, QuizService quizService, QuestionService questionService) {
        this.categoryService = categoryService;
        this.quizService = quizService;
        this.questionService = questionService;
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        model.addAttribute("hasOngoingQuiz", session.getAttribute("quizId") != null);
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<Category> categories = categoryService.getAllCategories();
        List<Quiz> quizHistory = quizService.getQuizzesByUserId(user.getUserId());

        model.addAttribute("categories", categories);
        model.addAttribute("quizHistory", quizHistory);

         if (user.isAdmin()) {
             return "redirect:/admin-home";
         }
         return "home";
    }

    @PostMapping("/quiz/start")
    public String startQuiz(@RequestParam("categoryId") int categoryId, HttpSession session) {


        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        if (session.getAttribute("quizQuestions") != null &&
                session.getAttribute("quizId") != null &&
                session.getAttribute("categoryId") != null) {

            // User already has a quiz in progress — redirect them to take it
            return "redirect:/quiz/take";
        }

        // Get 3 random questions for the selected category
        List<Question> questions = questionService.getRandomQuestionsByCategory(3,categoryId);

        // Save the quiz and get the generated quizId
        int quizId = quizService.startQuiz(user.getUserId(), categoryId, "Quiz for category " + categoryId,
                questions.stream()
                        .map(Question::getQuestionId)
                        .collect(Collectors.toList()));

        // Store quiz metadata in session
        session.setAttribute("quizQuestions", questions);
        session.setAttribute("quizId", quizId);
        session.setAttribute("categoryId", categoryId);

        return "redirect:/quiz/take";
    }

}
