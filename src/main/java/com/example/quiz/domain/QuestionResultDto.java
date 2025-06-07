package com.example.quiz.domain;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestionResultDto {
    private Question question;
    private int selectedChoiceId;
    private int correctChoiceId;
}
