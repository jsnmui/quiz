package com.example.quiz.domain;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Contact {
    private int id;
    private String subject;
    private String email;
    private String message;
    private Timestamp time;
}
