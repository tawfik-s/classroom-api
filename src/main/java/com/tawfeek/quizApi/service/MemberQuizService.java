package com.tawfeek.quizApi.service;

import com.tawfeek.quizApi.model.quiz.QuizResponseWithQuestionsDTO;

public interface MemberQuizService {

    public QuizResponseWithQuestionsDTO StartTakingTheQuiz(Long classroomId,Long quizId);
}
