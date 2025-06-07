package com.example.quiz.dao;

import com.example.quiz.domain.QuizResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QuizResultDao {


    private final JdbcTemplate jdbcTemplate;

    public QuizResultDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<QuizResult> getQuizResults(int offset, int limit, Integer categoryId, Integer userId) {
        StringBuilder sql = new StringBuilder("SELECT q.quiz_id, q.time_start, q.time_end, u.firstname, u.lastname, q.category_id, COUNT(qq.question_id) AS num_questions, ");
        sql.append("SUM(CASE WHEN qq.user_choice_id = c.choice_id AND c.is_correct THEN 1 ELSE 0 END) AS score ");
        sql.append("FROM quiz q ");
        sql.append("JOIN user u ON q.user_id = u.user_id ");
        sql.append("JOIN quizquestion qq ON q.quiz_id = qq.quiz_id ");
        sql.append("JOIN choice c ON qq.user_choice_id = c.choice_id ");
        sql.append("JOIN question ques ON qq.question_id = ques.question_id ");
        sql.append("WHERE 1=1 ");

        if (categoryId != null) sql.append("AND q.category_id = ").append(categoryId).append(" ");
        if (userId != null) sql.append("AND q.user_id = ").append(userId).append(" ");

        sql.append("GROUP BY q.quiz_id, q.time_start, q.time_end, u.firstname, u.lastname, q.category_id ");
        sql.append("ORDER BY q.time_end DESC LIMIT ? OFFSET ?");

        return jdbcTemplate.query(sql.toString(), new QuizResultRowMapper(), limit, offset);
    }

    public int countQuizResults(Integer categoryId, Integer userId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(DISTINCT q.quiz_id) FROM quiz q WHERE 1=1 ");
        if (categoryId != null) sql.append("AND q.category_id = ").append(categoryId).append(" ");
        if (userId != null) sql.append("AND q.user_id = ").append(userId).append(" ");
        return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
    }

    public QuizResult getQuizResultById(int quizId) {
        String sql = "SELECT q.quiz_id, q.time_start, q.time_end, u.firstname, u.lastname, q.category_id, " +
                "(SELECT COUNT(*) FROM quizquestion WHERE quiz_id = ?) AS num_questions, " +
                "(SELECT COUNT(*) FROM quizquestion qq JOIN choice c ON qq.user_choice_id = c.choice_id WHERE quiz_id = ? AND c.is_correct) AS score " +
                "FROM quiz q JOIN user u ON q.user_id = u.user_id WHERE q.quiz_id = ?";
        return jdbcTemplate.queryForObject(sql, new QuizResultRowMapper(), quizId, quizId, quizId);
    }

    private static class QuizResultRowMapper implements RowMapper<QuizResult> {
        @Override
        public QuizResult mapRow(ResultSet rs, int rowNum) throws SQLException {
            QuizResult result = new QuizResult();
            result.setQuizId(rs.getInt("quiz_id"));
            result.setStartTime(rs.getTimestamp("time_start"));
            result.setEndTime(rs.getTimestamp("time_end"));
            result.setUserFullName(rs.getString("firstname") + " " + rs.getString("lastname"));
            result.setCategoryId(rs.getInt("category_id"));
            result.setNumQuestions(rs.getInt("num_questions"));
            result.setScore(rs.getInt("score"));
            return result;
        }
    }
}
