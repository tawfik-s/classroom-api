package com.tawfeek.quizApi.controller;

import com.tawfeek.quizApi.model.question.AdminQuestionRequestDTO;
import com.tawfeek.quizApi.model.quiz.*;
import com.tawfeek.quizApi.service.AdminQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/classrooms")
public class AdminQuizController {

  @Autowired private AdminQuizService adminQuizService;

  @PostMapping("/{classroomId}/quizzes")
  public QuizResponseDTO createQuiz(
      @PathVariable Long classroomId, @RequestBody QuizRequestDTO quizRequestDTO) {
    return adminQuizService.createQuiz(classroomId, quizRequestDTO);
  }

  @PostMapping("/{classroomId}/quizzes/{quizId}/questions")
  public AdminQuizWithQuestionsResponseDTO addQuestion(
      @PathVariable Long classroomId,
      @PathVariable Long quizId,
      @RequestBody AdminQuestionRequestDTO questionRequest) {

    return adminQuizService.addQuestion(classroomId, quizId, questionRequest);
  }

  @PostMapping("/{classroomId}/quizzes/with-questions")
  public AdminQuizWithQuestionsResponseDTO createQuizWithQuestions(
      @PathVariable Long classroomId,
      @RequestBody AdminQuizWithQuestionRequestDTO adminQuizWithQuestionRequestDTO) {
    return adminQuizService.createQuizWithQuestions(classroomId, adminQuizWithQuestionRequestDTO);
  }

  @GetMapping("{classroomId}/quizzes")
  public List<QuizResponseDTO> getClassRoomQuizzes(@PathVariable Long classroomId) {
    return adminQuizService.getClassroomQuizzes(classroomId);
  }
  @PutMapping("/{classroomId}/quizzes/{quizId}")
  public AdminQuizWithQuestionsResponseDTO updateQuiz(
          @PathVariable Long classroomId,
          @PathVariable Long quizId,
          @RequestBody QuizRequestDTO quizRequestDTO) {
    return adminQuizService.updateQuiz(classroomId, quizId, quizRequestDTO);
  }

  @PutMapping("/{classroomId}/quizzes/{quizId}/questions/{questionId}")
  public AdminQuizWithQuestionsResponseDTO updateQuestion(
          @PathVariable Long classroomId,
          @PathVariable Long quizId,
          @PathVariable Long questionId,
          @RequestBody AdminQuestionRequestDTO adminQuestionRequestDTO) {
    return adminQuizService.updateQuestion(classroomId, quizId,questionId, adminQuestionRequestDTO);
  }

  @GetMapping("{classroomId}/quizzes/{quizId}/with-questions")
  public AdminQuizWithQuestionsResponseDTO getClassRoomQuizWithQuestions(
      @PathVariable Long classroomId, @PathVariable Long quizId) {
    return adminQuizService.getQuizWithQuestionsById(classroomId, quizId);
  }

  @DeleteMapping("{classroomId}/quizzes/{quizId}/questions/{questionId}")
  public void deleteQuiz(
      @PathVariable Long classroomId, @PathVariable Long quizId, @PathVariable Long questionId) {
    adminQuizService.deleteQuestion(classroomId, quizId, questionId);
  }

  @DeleteMapping("{classroomId}/quizzes/{quizId}")
  public void deleteQuiz(@PathVariable Long classroomId, @PathVariable Long quizId) {
    adminQuizService.deleteQuiz(classroomId, quizId);
  }
}
