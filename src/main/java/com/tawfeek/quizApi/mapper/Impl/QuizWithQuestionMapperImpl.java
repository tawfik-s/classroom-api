package com.tawfeek.quizApi.mapper.Impl;

import com.tawfeek.quizApi.entity.QuestionAnswer;
import com.tawfeek.quizApi.entity.Quiz;
import com.tawfeek.quizApi.entity.QuizAnswer;
import com.tawfeek.quizApi.mapper.QuestionMapper;
import com.tawfeek.quizApi.mapper.QuizWithQuestionMapper;
import com.tawfeek.quizApi.model.quiz.QuizResponseWithQuestionsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class QuizWithQuestionMapperImpl implements QuizWithQuestionMapper {
  @Autowired private QuestionMapper questionMapper;

  @Override
  public QuizResponseWithQuestionsDTO toDTO(Quiz quiz) {
    QuizResponseWithQuestionsDTO quizResponseDTO = new QuizResponseWithQuestionsDTO();
    quizResponseDTO.setId(quiz.getId());
    quizResponseDTO.setName(quiz.getName());
    quizResponseDTO.setDuration(quiz.getDuration());
    quizResponseDTO.setCreationDateTime(quiz.getCreationDateTime());
    quizResponseDTO.setCloseDate(quiz.getCloseDate());
    quizResponseDTO.setQuestions(
        quiz.getQuestions().stream().map(question -> questionMapper.toDTO(question)).toList());
    return quizResponseDTO;
  }

  @Override
  public QuizResponseWithQuestionsDTO toDTO(Quiz quiz, QuizAnswer quizAnswer) {
    QuizResponseWithQuestionsDTO quizResponseDTO = new QuizResponseWithQuestionsDTO();
    quizResponseDTO.setId(quiz.getId());
    quizResponseDTO.setName(quiz.getName());
    quizResponseDTO.setDuration(quiz.getDuration());
    quizResponseDTO.setCreationDateTime(quiz.getCreationDateTime());
    quizResponseDTO.setCloseDate(quiz.getCloseDate());
    quizResponseDTO.setQuestions(
        quiz.getQuestions().stream()
            .map(
                question ->
                    questionMapper.toDTO(
                        question,
                        quizAnswer.getQuestionAnswers().stream()
                            .filter(
                                questionAnswer ->
                                        Objects.equals(questionAnswer.getQuestion().getId(), question.getId()))
                            .findFirst()
                            .orElse(new QuestionAnswer())))
            .toList());
    return quizResponseDTO;
  }
}
