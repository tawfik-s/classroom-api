package com.tawfeek.quizApi.mapper.Impl;

import com.tawfeek.quizApi.entity.Question;
import com.tawfeek.quizApi.mapper.AdminQuestionMapper;
import com.tawfeek.quizApi.model.question.AdminQuestionRequestDTO;
import com.tawfeek.quizApi.model.question.AdminQuestionResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AdminQuestionMapperImpl implements AdminQuestionMapper {

  @Override
  public AdminQuestionResponseDTO toDTO(Question question) {
    AdminQuestionResponseDTO adminQuestionResponseDTO = new AdminQuestionResponseDTO();
    adminQuestionResponseDTO.setId(question.getId());
    adminQuestionResponseDTO.setText(question.getText());
    adminQuestionResponseDTO.setOptionA(question.getOptionA());
    adminQuestionResponseDTO.setOptionB(question.getOptionB());
    adminQuestionResponseDTO.setOptionC(question.getOptionC());
    adminQuestionResponseDTO.setOptionD(question.getOptionD());
    adminQuestionResponseDTO.setCorrectAnswer(question.getCorrectAnswers());
    return adminQuestionResponseDTO;
  }

  @Override
  public Question toEntity(AdminQuestionRequestDTO adminQuestionRequestDTO) {
    Question question = new Question();
    question.setText(adminQuestionRequestDTO.getText());
    question.setOptionA(adminQuestionRequestDTO.getOptionA());
    question.setOptionB(adminQuestionRequestDTO.getOptionB());
    question.setOptionC(adminQuestionRequestDTO.getOptionC());
    question.setOptionD(adminQuestionRequestDTO.getOptionD());
    question.setCorrectAnswers(adminQuestionRequestDTO.getCorrectAnswer());
    return question;
  }
}
