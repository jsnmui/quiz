package com.example.quiz.dao;

import com.example.quiz.domain.Quiz;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class QuizDao {

    private final JdbcTemplate jdbcTemplate;

    public QuizDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Quiz getQuizById(int quizId) {
        String sql = "SELECT * FROM Quiz WHERE quiz_id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Quiz.class), quizId);
    }

    public List<Quiz> getQuizzesByUserId(int userId) {
        String sql = "SELECT * FROM Quiz WHERE user_id = ? ORDER BY time_start DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Quiz.class), userId);
    }


    public int createQuizAndReturnId(Quiz quiz) {
        String sql = "INSERT INTO Quiz (user_id, category_id, name, time_start) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, quiz.getUserId());
            ps.setInt(2, quiz.getCategoryId());
            ps.setString(3, quiz.getName());
            ps.setTimestamp(4, quiz.getTimeStart());
            return ps;
        }, keyHolder);

        return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;
    }


    public void saveQuizQuestions(int quizId, List<Integer> questionIds) {
        String sql = "INSERT INTO quizquestion (quiz_id, question_id) VALUES (?, ?)";
        for (Integer qid : questionIds) {
            jdbcTemplate.update(sql, quizId, qid);
        }
    }

    public void updateQuizEndTime(int quizId, Timestamp endTime) {
        String sql = "UPDATE quiz SET time_end = ? WHERE quiz_id = ?";
        jdbcTemplate.update(sql, endTime, quizId);
    }
}