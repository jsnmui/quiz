package com.example.quiz.domain;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
public class QuizResult {
    private int quizId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String categoryName;
    private String userFullName;
    private int categoryId;
    private int numQuestions;
    private int score;
}