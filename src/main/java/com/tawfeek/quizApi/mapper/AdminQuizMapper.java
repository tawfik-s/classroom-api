package com.tawfeek.quizApi.mapper;

import com.tawfeek.quizApi.entity.Question;
import com.tawfeek.quizApi.entity.Quiz;
import com.tawfeek.quizApi.model.quiz.AdminQuizWithQuestionRequestDTO;
import com.tawfeek.quizApi.model.quiz.AdminQuizWithQuestionsResponseDTO;

import java.util.List;

public interface AdminQuizMapper {

  public AdminQuizWithQuestionsResponseDTO toDTO(Quiz quiz);

  public Quiz toEntity(
          AdminQuizWithQuestionRequestDTO quizCreationWithQuestionsDTO);
}
