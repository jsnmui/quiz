package com.example.quiz.dao;


import com.example.quiz.domain.Choice;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChoiceRowMapper implements RowMapper<Choice> {
    @Override
    public Choice mapRow(ResultSet rs, int rowNum) throws SQLException {
        Choice choice = new Choice();
        choice.setChoiceId(rs.getInt("choice_id"));
        choice.setQuestionId(rs.getInt("question_id"));
        choice.setDescription(rs.getString("description"));
        choice.setCorrect(rs.getBoolean("is_correct")); // maps is_correct → isCorrect
        return choice;
    }
}
