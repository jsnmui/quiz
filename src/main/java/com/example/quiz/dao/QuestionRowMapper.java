package com.example.quiz.dao;



import com.example.quiz.domain.Question;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionRowMapper implements RowMapper<Question> {
    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
        Question question = new Question();
        question.setQuestionId(rs.getInt("question_id"));
        question.setCategoryId(rs.getInt("category_id"));
        question.setDescription(rs.getString("description"));
        question.setActive(rs.getBoolean("is_active")); // maps to boolean isActive
        return question;
    }
}

