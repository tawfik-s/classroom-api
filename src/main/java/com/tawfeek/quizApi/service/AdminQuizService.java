package com.tawfeek.quizApi.service;

import com.tawfeek.quizApi.model.question.AdminQuestionRequestDTO;
import com.tawfeek.quizApi.model.quiz.AdminQuizWithQuestionRequestDTO;
import com.tawfeek.quizApi.model.quiz.QuizRequestDTO;
import com.tawfeek.quizApi.model.quiz.QuizResponseDTO;
import com.tawfeek.quizApi.model.quiz.AdminQuizWithQuestionsResponseDTO;

import java.util.List;

public interface AdminQuizService {

  public QuizResponseDTO createQuiz(Long classroomId, QuizRequestDTO quizRequestDTO);

  public List<QuizResponseDTO> getClassroomQuizzes(Long classroomId);

  public AdminQuizWithQuestionsResponseDTO addQuestion(
      long classroomId, long quizId, AdminQuestionRequestDTO questionRequest);

  public AdminQuizWithQuestionsResponseDTO createQuizWithQuestions(
          Long classroomId, AdminQuizWithQuestionRequestDTO quizRequest);

    void deleteQuiz(Long classroomId, Long quizId);

  void deleteQuestion(Long classroomId, Long quizId, Long questionId);
}
