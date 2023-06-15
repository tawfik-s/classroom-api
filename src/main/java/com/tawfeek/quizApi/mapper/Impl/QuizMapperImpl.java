package com.tawfeek.quizApi.mapper.Impl;

import com.tawfeek.quizApi.entity.Quiz;
import com.tawfeek.quizApi.mapper.QuizMapper;
import com.tawfeek.quizApi.model.quiz.QuizRequestDTO;
import com.tawfeek.quizApi.model.quiz.QuizResponseDTO;
import org.springframework.stereotype.Component;


@Component
public class QuizMapperImpl implements QuizMapper {
    @Override
    public Quiz toEntity(QuizRequestDTO quizRequestDTO) {
        Quiz quiz = new Quiz();
        quiz.setName(quizRequestDTO.getName());
        quiz.setDuration(quizRequestDTO.getDuration());
        quiz.setCreationDateTime(quizRequestDTO.getCreationDateTime());
        quiz.setCloseDate(quizRequestDTO.getCloseDate());
        return quiz;
    }

    @Override
    public QuizResponseDTO toDTO(Quiz quiz) {
        return new QuizResponseDTO(quiz.getId(),
                quiz.getName(),
                quiz.getCreationDateTime(),
                quiz.getDuration(),
                quiz.getCloseDate());
    }
}
