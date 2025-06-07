package com.example.quiz.dao;

import com.example.quiz.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
public class UserRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setActive(rs.getBoolean("is_active"));
        user.setAdmin(rs.getBoolean("is_admin"));
        return user;
    }
}