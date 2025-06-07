package com.example.quiz.service;

import com.example.quiz.dao.QuestionDao;
import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private final QuestionDao questionDao;

    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }


    public List<Question> getRandomQuestionsByCategory(int count, int categoryId) {
        List<Question> all = questionDao.getActiveQuestionsByCategory(categoryId);
        Collections.shuffle(all);
        return all.stream().limit(count).collect(Collectors.toList());
    }

    public List<Question> getQuestionsByQuizId(int quizId) {
        // get all questions and their choices for the quiz
        return questionDao.getQuestionsByQuizId(quizId);
    }

    public int getSelectedChoiceId(int quizId, int questionId) {
        // get what the user selected for this question in this quiz
        return questionDao.getSelectedChoiceId(quizId, questionId);
    }
    public List<Question> getAllQuestionsWithChoicesAndCategory() {
        return questionDao.findAllWithChoicesAndCategory();
    }

    public void toggleStatus(int questionId) {
        questionDao.toggleQuestionStatus(questionId);
    }


    public Question getQuestionById(int questionId) {
        return questionDao.findById(questionId);
    }

    public List<Choice> getChoicesForQuestion(int questionId) {
        return questionDao.findChoicesByQuestionId(questionId);
    }

    public void updateQuestionWithChoices(int questionId, String description, List<String> choices, int correctChoiceId) {
        questionDao.updateQuestion(questionId, description);
        questionDao.updateChoices(questionId, choices, correctChoiceId);
    }

    public List<Category> getAllCategories() {
        return questionDao.getAllCategories();
    }

    public void createQuestionWithChoices(int categoryId, String description, List<String> choiceDescriptions, int correctChoiceIndex) {
        List<Choice> choices = new ArrayList<>();
        for (int i = 0; i < choiceDescriptions.size(); i++) {
            Choice choice = new Choice();
            choice.setDescription(choiceDescriptions.get(i));
            choice.setCorrect(i == correctChoiceIndex);
            choices.add(choice);
        }

        questionDao.createQuestionWithChoices(categoryId, description, choices);
    }




}
