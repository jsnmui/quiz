package com.example.quiz.dao;

import com.example.quiz.domain.Category;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDao {

    private final JdbcTemplate jdbcTemplate;

    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM Category";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }
}
