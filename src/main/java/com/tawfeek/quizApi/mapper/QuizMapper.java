package com.tawfeek.quizApi.mapper;

import com.tawfeek.quizApi.entity.Quiz;
import com.tawfeek.quizApi.model.quiz.QuizRequestDTO;
import com.tawfeek.quizApi.model.quiz.QuizResponseDTO;

public interface QuizMapper {

    public Quiz toEntity(QuizRequestDTO quizRequestDTO);

    public QuizResponseDTO toDTO(Quiz quiz);
}
