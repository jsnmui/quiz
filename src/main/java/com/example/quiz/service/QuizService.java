package com.example.quiz.service;

import com.example.quiz.dao.QuizDao;
import com.example.quiz.domain.Quiz;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class QuizService {

    private final QuizDao quizDao;

    public QuizService(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    public List<Quiz> getQuizzesByUserId(int userId) {
        return quizDao.getQuizzesByUserId(userId);
    }

    public int startQuiz(int userId, int categoryId, String quizName, List<Integer> questionIds) {
        Quiz quiz = new Quiz();
        quiz.setUserId(userId);
        quiz.setCategoryId(categoryId);
        quiz.setName(quizName);
        quiz.setTimeStart(new Timestamp(System.currentTimeMillis()));
        int quizId = quizDao.createQuizAndReturnId(quiz);
        quizDao.saveQuizQuestions(quizId, questionIds);
        return quizId;
    }

    public void updateQuizEndTime(int quizId, Timestamp endTime) {
        quizDao.updateQuizEndTime(quizId, endTime);
    }


    public Quiz getQuizById(int quizId) {
        return quizDao.getQuizById(quizId);
    }

}