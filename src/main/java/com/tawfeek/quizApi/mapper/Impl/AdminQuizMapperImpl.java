package com.tawfeek.quizApi.mapper.Impl;

import com.tawfeek.quizApi.entity.Question;
import com.tawfeek.quizApi.entity.Quiz;
import com.tawfeek.quizApi.mapper.AdminQuestionMapper;
import com.tawfeek.quizApi.mapper.AdminQuizMapper;
import com.tawfeek.quizApi.model.quiz.AdminQuizWithQuestionRequestDTO;
import com.tawfeek.quizApi.model.quiz.AdminQuizWithQuestionsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminQuizMapperImpl implements AdminQuizMapper {

  @Autowired AdminQuestionMapper adminQuestionMapper;

  @Override
  public AdminQuizWithQuestionsResponseDTO toDTO(Quiz quiz) {
    AdminQuizWithQuestionsResponseDTO quizResponse =
        new AdminQuizWithQuestionsResponseDTO();
    quizResponse.setName(quiz.getName());
    quizResponse.setDuration(quiz.getDuration());
    quizResponse.setId(quiz.getId());
    quizResponse.setCloseDate(quiz.getCloseDate());
    quizResponse.setCreationDateTime(quiz.getCreationDateTime());
    quizResponse.setQuestions(
        quiz.getQuestions().stream().map(question -> adminQuestionMapper.toDTO(question)).toList());
    return quizResponse;
  }

  @Override
  public Quiz toEntity(AdminQuizWithQuestionRequestDTO quizRequest, List<Question> questions) {
    Quiz quiz = new Quiz();
    quiz.setName(quizRequest.getName());
    quiz.setDuration(quizRequest.getDuration());
    quiz.setCreationDateTime(quizRequest.getCreationDateTime());
    quiz.setCloseDate(quizRequest.getCloseDate());
    quiz.setQuestions(questions);
    return quiz;
  }
}
