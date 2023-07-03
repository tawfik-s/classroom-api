package com.tawfeek.quizApi.mapper;

import com.tawfeek.quizApi.entity.Question;
import com.tawfeek.quizApi.model.question.AdminQuestionRequestDTO;
import com.tawfeek.quizApi.model.question.AdminQuestionResponseDTO;
import org.springframework.stereotype.Component;

public interface AdminQuestionMapper {
  public AdminQuestionResponseDTO toDTO(Question question);

  public Question toEntity(AdminQuestionRequestDTO adminQuestionRequestDTO);
}
