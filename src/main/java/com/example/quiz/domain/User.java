package com.example.quiz.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private int userId;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private boolean active;  // maps to `is_active` in DB
    private boolean admin;   // maps to `is_admin` in DB
}

