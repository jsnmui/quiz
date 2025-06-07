package com.example.quiz.controller;



import com.example.quiz.domain.*;
import com.example.quiz.service.QuizService;
import com.example.quiz.service.QuizAnswerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class QuizController {


    private final QuizAnswerService quizAnswerService;
    private final QuizService quizService;

    @GetMapping("/quiz/take")
    public String takeQuiz(Model model, HttpSession session) {
        Integer categoryId = (Integer) session.getAttribute("categoryId");
        if (categoryId == null) return "redirect:/home";

        List<Question> questions = (List<Question>) session.getAttribute("quizQuestions");

        // Check if questions exist in session
        if (questions == null) {
            return "redirect:/home";
        }


        session.setAttribute("quizStartTime", new Timestamp(System.currentTimeMillis()));

        model.addAttribute("questions", questions);
        return "quiz";
    }

    @PostMapping("/quiz/submit")
    public String submitQuiz(@RequestParam Map<String, String> formData,
                             HttpSession session,
                             Model model) {

        List<Question> questions = (List<Question>) session.getAttribute("quizQuestions");
        Timestamp startTime = (Timestamp) session.getAttribute("quizStartTime");
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        User user = (User) session.getAttribute("user");
        Integer quizId = (Integer) session.getAttribute("quizId");

        if (quizId != null) {
            quizService.updateQuizEndTime(quizId, endTime);
        }

        List<QuestionResultDto> results = new ArrayList<>();
        int score = 0;

        for (Question question : questions) {
            String param = "question_" + question.getQuestionId();
            int selectedId = Integer.parseInt(formData.getOrDefault(param, "-1"));

            int correctId = question.getChoices().stream()
                    .filter(Choice::isCorrect)
                    .map(Choice::getChoiceId)
                    .findFirst().orElse(-1);

            if (quizId != null && selectedId != -1) {
                quizAnswerService.saveAnswer(quizId, question.getQuestionId(), selectedId);
            }

            QuestionResultDto result = new QuestionResultDto();
            result.setQuestion(question);
            result.setSelectedChoiceId(selectedId);
            result.setCorrectChoiceId(correctId);

            if (selectedId == correctId) {
                score++;
            }

            results.add(result);
        }

        model.addAttribute("user", user);
        Map<String, Object> quizInfo = new HashMap<>();
        quizInfo.put("name", "User Quiz");
        model.addAttribute("quiz", quizInfo);
        model.addAttribute("score", score);
        model.addAttribute("total", questions.size());
        model.addAttribute("questionResults", results);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);

        session.removeAttribute("quizQuestions");
        session.removeAttribute("quizId");
        session.removeAttribute("categoryId");
        session.removeAttribute("quizStartTime");

        return "quiz-result";
    }
}
