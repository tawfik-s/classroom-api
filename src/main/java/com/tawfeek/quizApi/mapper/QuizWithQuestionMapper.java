package com.tawfeek.quizApi.mapper;

import com.tawfeek.quizApi.entity.Quiz;
import com.tawfeek.quizApi.entity.QuizAnswer;
import com.tawfeek.quizApi.model.quiz.QuizResponseWithQuestionsDTO;

public interface QuizWithQuestionMapper {
    QuizResponseWithQuestionsDTO toDTO(Quiz quiz);

    QuizResponseWithQuestionsDTO toDTO(Quiz quiz, QuizAnswer quizAnswer);
}
