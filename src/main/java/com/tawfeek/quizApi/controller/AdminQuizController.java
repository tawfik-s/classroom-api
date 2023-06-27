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
          @RequestBody AdminQuizWithQuestionRequestDTO adminQuizWithQuestionRequestDTO
          ) {
    return adminQuizService.createQuizWithQuestions(classroomId,adminQuizWithQuestionRequestDTO);
  }

  @GetMapping("{classroomId}/quizzes")
  public List<QuizResponseDTO> getClassRoomQuizzes(@PathVariable Long classroomId) {
    return adminQuizService.getClassroomQuizzes(
        classroomId);
  }

  @DeleteMapping("/quizzes/{quizId}")
  public void deleteQuiz(@PathVariable Long quizId) {
    // Implementation goes here
  }

}