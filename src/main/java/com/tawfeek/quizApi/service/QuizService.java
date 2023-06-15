package com.tawfeek.quizApi.service;

import com.tawfeek.quizApi.model.quiz.QuizRequestDTO;
import com.tawfeek.quizApi.model.quiz.QuizResponseDTO;

public interface QuizService {

    public QuizResponseDTO createQuiz(Long classroomId,QuizRequestDTO quizRequestDTO);

}
