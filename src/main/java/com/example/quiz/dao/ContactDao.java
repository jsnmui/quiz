package com.example.quiz.dao;


import com.example.quiz.domain.Contact;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ContactDao {

    private final JdbcTemplate jdbcTemplate;

    public ContactDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Contact contact) {
        String sql = "INSERT INTO Contact (subject, email, message) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, contact.getSubject(), contact.getEmail(), contact.getMessage());
    }


    public List<Contact> getAllContacts() {
        String sql = "SELECT * FROM Contact ORDER BY time DESC";
        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    public Contact getContactById(int id) {
        String sql = "SELECT * FROM Contact WHERE contact_id = ?";
        return jdbcTemplate.queryForObject(sql, new ContactRowMapper(), id);
    }

    private static class ContactRowMapper implements RowMapper<Contact> {
        @Override
        public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
            Contact contact = new Contact();
            contact.setId(rs.getInt("contact_id"));
            contact.setSubject(rs.getString("subject"));
            contact.setEmail(rs.getString("email"));
            contact.setMessage(rs.getString("message"));
            contact.setTime(rs.getTimestamp("time")); // requires `private Timestamp time;` in Contact class
            return contact;
        }
    }
}
