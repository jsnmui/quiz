package com.example.quiz.dao;

import com.example.quiz.domain.QuizResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuizResultDao {


    private final JdbcTemplate jdbcTemplate;

    public QuizResultDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
            result.setCategoryName(rs.getString("category_name"));
            result.setNumQuestions(rs.getInt("num_questions"));
            result.setScore(rs.getInt("score"));
            return result;
        }
    }


    public List<QuizResult> getQuizResults(int offset, int limit, String categoryName, String userName) {
        StringBuilder sql = new StringBuilder("SELECT q.quiz_id, q.time_start, q.time_end, q.category_id, u.firstname, u.lastname, " +
                "COUNT(qq.question_id) AS num_questions, SUM(CASE WHEN c.is_correct THEN 1 ELSE 0 END) AS score, " +
                "cat.name AS category_name " +
                "FROM quiz q " +
                "JOIN quizquestion qq ON q.quiz_id = qq.quiz_id " +
                "JOIN choice c ON qq.user_choice_id = c.choice_id " +
                "JOIN user u ON q.user_id = u.user_id JOIN category cat ON q.category_id = cat.category_id WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (categoryName != null && !categoryName.isEmpty()) {
            sql.append(" AND cat.name LIKE ?");
            params.add("%" + categoryName + "%");
        }

        if (userName != null && !userName.isEmpty()) {
            sql.append(" AND CONCAT(u.firstname, ' ', u.lastname) LIKE ?");
            params.add("%" + userName + "%");
        }

        sql.append(" GROUP BY q.quiz_id, q.time_start, q.time_end, q.category_id, u.firstname, u.lastname, cat.name  ORDER BY q.time_end DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        return jdbcTemplate.query(sql.toString(), params.toArray(), new QuizResultRowMapper());
    }

    public int countQuizResults(String categoryName, String userName) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(DISTINCT q.quiz_id) FROM quiz q " +
                "JOIN user u ON q.user_id = u.user_id " +
                "JOIN category cat ON q.category_id = cat.category_id WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (categoryName != null && !categoryName.isEmpty()) {
            sql.append(" AND cat.name LIKE ?");
            params.add("%" + categoryName + "%");
        }

        if (userName != null && !userName.isEmpty()) {
            sql.append(" AND CONCAT(u.firstname, ' ', u.lastname) LIKE ?");
            params.add("%" + userName + "%");
        }

        return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
    }

}
