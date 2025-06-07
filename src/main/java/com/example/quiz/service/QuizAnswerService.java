package com.example.quiz.service;


import com.example.quiz.dao.QuizAnswerDao;
import org.springframework.stereotype.Service;

@Service
public class QuizAnswerService {

    private final QuizAnswerDao quizAnswerDao;

    public QuizAnswerService(QuizAnswerDao quizAnswerDao) {
        this.quizAnswerDao = quizAnswerDao;
    }

    public void saveAnswer(int quizId, int questionId, int selectedChoiceId) {
        quizAnswerDao.saveQuizAnswer(quizId, questionId, selectedChoiceId);
    }
}
