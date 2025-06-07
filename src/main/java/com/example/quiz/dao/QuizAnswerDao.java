package com.example.quiz.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QuizAnswerDao {
    private final JdbcTemplate jdbcTemplate;

    public QuizAnswerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void saveQuizAnswer(int quizId, int questionId, int selectedChoiceId) {
        String sql = "UPDATE quizquestion SET user_choice_id = ? WHERE quiz_id = ? AND question_id = ?";
        jdbcTemplate.update(sql, selectedChoiceId, quizId, questionId);
    }

}
