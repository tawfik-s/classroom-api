package com.tawfeek.quizApi.mapper;

import com.tawfeek.quizApi.entity.Question;
import com.tawfeek.quizApi.entity.QuestionAnswer;
import com.tawfeek.quizApi.model.question.QuestionResponseDTO;

public interface QuestionMapper {

    public QuestionResponseDTO toDTO(Question question);

    public QuestionResponseDTO toDTO(Question question, QuestionAnswer questionAnswer);
}
