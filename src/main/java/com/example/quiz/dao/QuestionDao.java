package com.example.quiz.dao;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository
public class QuestionDao {

    private final JdbcTemplate jdbcTemplate;

    public QuestionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Question> getActiveQuestionsByCategory(int categoryId) {
        String sql = "SELECT * FROM Question WHERE category_id = ? AND is_active = true";
        List<Question> questions = jdbcTemplate.query(sql, new QuestionRowMapper(), categoryId);

        for (Question question : questions) {
            List<Choice> choices = getChoicesForQuestion(question.getQuestionId());
            question.setChoices(choices);
        }

        return questions;
    }
    private List<Choice> getChoicesForQuestion(int questionId) {
        String sql = "SELECT * FROM Choice WHERE question_id = ?";
        return jdbcTemplate.query(sql, new ChoiceRowMapper(), questionId);
    }

    public List<Question> getQuestionsByQuizId(int quizId) {
        String sql = "SELECT q.* FROM Question q " +
                "JOIN quizquestion qq ON q.question_id = qq.question_id " +
                "WHERE qq.quiz_id = ?";
        List<Question> questions = jdbcTemplate.query(sql, new QuestionRowMapper(), quizId);

        for (Question question : questions) {
            question.setChoices(getChoicesForQuestion(question.getQuestionId()));
        }

        return questions;
    }

    public Integer getSelectedChoiceId(int quizId, int questionId) {
        String sql = "SELECT user_choice_id FROM quizquestion WHERE quiz_id = ? AND question_id = ?";
        List<Integer> result = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("user_choice_id"), quizId, questionId);
        return result.isEmpty() ? null : result.get(0);
    }


    public List<Question> findAllWithChoicesAndCategory() {
        String sql = "SELECT q.*, c.name AS category_name FROM Question q JOIN Category c ON q.category_id = c.category_id ORDER BY q.question_id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Question q = new Question();
            q.setQuestionId(rs.getInt("question_id"));
            q.setDescription(rs.getString("description"));
            q.setActive(rs.getBoolean("is_active"));
            q.setCategoryId(rs.getInt("category_id"));
            q.setCategoryName(rs.getString("category_name"));  // add field to Question model
            return q;
        });
    }

    public void toggleQuestionStatus(int questionId) {
        String sql = "UPDATE Question SET is_active = NOT is_active WHERE question_id = ?";
        jdbcTemplate.update(sql, questionId);
    }

    public Question findById(int questionId) {
        String sql = "SELECT * FROM Question WHERE question_id = ?";
        return jdbcTemplate.queryForObject(sql, new QuestionRowMapper(), questionId);
    }

    public List<Choice> findChoicesByQuestionId(int questionId) {
        String sql = "SELECT * FROM Choice WHERE question_id = ?";
        return jdbcTemplate.query(sql, new ChoiceRowMapper(), questionId);
    }

    public void updateQuestion(int questionId, String description) {
        String sql = "UPDATE Question SET description = ? WHERE question_id = ?";
        jdbcTemplate.update(sql, description, questionId);
    }

    public void updateChoices(int questionId, List<String> choices, int correctChoiceId) {
        String sql = "UPDATE Choice SET description = ?, is_correct = ? WHERE question_id = ? AND choice_id = ?";
        List<Choice> existing = findChoicesByQuestionId(questionId);
        for (int i = 0; i < choices.size(); i++) {
            Choice choice = existing.get(i);
            boolean isCorrect = (choice.getChoiceId() == correctChoiceId);
            jdbcTemplate.update(sql, choices.get(i), isCorrect, questionId, choice.getChoiceId());
        }
    }

    public int insertQuestion(int categoryId, String description) {
        String sql = "INSERT INTO Question (category_id, description, is_active) VALUES (?, ?, true)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, categoryId);
            ps.setString(2, description);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void insertChoice(int questionId, String choiceText, boolean isCorrect) {
        String sql = "INSERT INTO Choice (question_id, description, is_correct) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, questionId, choiceText, isCorrect);
    }



    public List<Category> getAllCategories() {
        String sql = "SELECT category_id, name FROM Category ORDER BY name";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Category cat = new Category();
            cat.setCategoryId(rs.getInt("category_id"));
            cat.setName(rs.getString("name"));
            return cat;
        });
    }

    public void createQuestionWithChoices(int categoryId, String description, List<Choice> choices) {
        int questionId = insertQuestion(categoryId, description);
        for (Choice choice : choices) {
            insertChoice(questionId, choice.getDescription(), choice.isCorrect());
        }
    }
}

