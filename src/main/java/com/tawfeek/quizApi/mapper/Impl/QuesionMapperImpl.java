package com.tawfeek.quizApi.mapper.Impl;

import com.tawfeek.quizApi.entity.Question;
import com.tawfeek.quizApi.entity.QuestionAnswer;
import com.tawfeek.quizApi.mapper.QuestionMapper;
import com.tawfeek.quizApi.model.question.QuestionResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class QuesionMapperImpl implements QuestionMapper {
  @Override
  public QuestionResponseDTO toDTO(Question question) {
    QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
    questionResponseDTO.setId(question.getId());
    questionResponseDTO.setText(question.getText());
    questionResponseDTO.setOptionA(question.getOptionA());
    questionResponseDTO.setOptionB(questionResponseDTO.getOptionB());
    questionResponseDTO.setOptionC(questionResponseDTO.getOptionC());
    questionResponseDTO.setOptionD(questionResponseDTO.getOptionD());
    questionResponseDTO.setLastAnswer("");
    return questionResponseDTO;
  }

  @Override
  public QuestionResponseDTO toDTO(Question question, QuestionAnswer questionAnswer) {
    String queans=(questionAnswer.getAnswer()==null)?"":questionAnswer.getAnswer();
    QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
    questionResponseDTO.setId(question.getId());
    questionResponseDTO.setText(question.getText());
    questionResponseDTO.setOptionA(question.getOptionA());
    questionResponseDTO.setOptionB(question.getOptionB());
    questionResponseDTO.setOptionC(question.getOptionC());
    questionResponseDTO.setOptionD(question.getOptionD());
    questionResponseDTO.setLastAnswer(queans);
    return questionResponseDTO;
  }
}
