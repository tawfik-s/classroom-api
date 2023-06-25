package com.tawfeek.quizApi.service;

import com.tawfeek.quizApi.model.quiz.QuizRequestDTO;
import com.tawfeek.quizApi.model.quiz.QuizResponseDTO;

import java.util.List;

public interface QuizService {

    public QuizResponseDTO createQuiz(Long classroomId,QuizRequestDTO quizRequestDTO);

    public List<QuizResponseDTO> getClassroomQuizzes(Long classroomId);

}
