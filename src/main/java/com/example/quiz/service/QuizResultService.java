package com.example.quiz.service;

import com.example.quiz.dao.QuizResultDao;
import com.example.quiz.domain.QuizResult;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class QuizResultService {
    private final QuizResultDao quizResultDao;

    public QuizResultService(QuizResultDao quizResultDao) {
        this.quizResultDao = quizResultDao;
    }

    public List<QuizResult> getQuizResults(int offset, int limit, Integer categoryId, Integer userId) {
        return quizResultDao.getQuizResults(offset, limit, categoryId, userId);
    }

    public int countQuizResults(Integer categoryId, Integer userId) {
        return quizResultDao.countQuizResults(categoryId, userId);
    }

    public QuizResult getQuizResultById(int quizId) {
        return quizResultDao.getQuizResultById(quizId);
    }
}
