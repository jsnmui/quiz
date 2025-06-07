package com.example.quiz.domain;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Question {
    private int questionId;
    private int categoryId;
    private String description;
    private boolean isActive;

    private List<Choice> choices;
    private String categoryName;
}