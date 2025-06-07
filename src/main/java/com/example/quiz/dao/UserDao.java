package com.example.quiz.dao;

import com.example.quiz.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM User WHERE email = ? AND password = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), email, password);
        return users.stream().findFirst();
    }


    public void createNewUser(User user) {
        String sql = "INSERT INTO User (email, password, firstname, lastname, is_active, is_admin) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getPassword(),
                user.getFirstname(),
                user.getLastname(),
                user.isActive(),
                user.isAdmin()
        );
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getInt("user_id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getBoolean("is_active"),
                    rs.getBoolean("is_admin")
            );
        }
    }
    public List<User> getUsersWithPagination(int offset, int limit) {
        System.out.println("Fetching users with LIMIT = " + limit + ", OFFSET = " + offset);
        String sql = "SELECT * FROM User ORDER BY user_id LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new UserRowMapper(), limit, offset);
    }

    public int getTotalUserCount() {
        String sql = "SELECT COUNT(*) FROM User";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public void updateUserStatus(int userId, boolean isActive) {
        String sql = "UPDATE User SET is_active = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, isActive, userId);
    }



}
