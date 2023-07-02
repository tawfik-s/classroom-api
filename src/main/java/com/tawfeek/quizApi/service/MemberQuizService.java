package com.tawfeek.quizApi.service;

import com.tawfeek.quizApi.model.questionAnswer.QuestionAnswerSubmitDTO;
import com.tawfeek.quizApi.model.quiz.QuizResponseWithQuestionsDTO;
import com.tawfeek.quizApi.model.quizAnswer.QuizAnswerSubmitDTO;
import org.springframework.http.ResponseEntity;

public interface MemberQuizService {

    public QuizResponseWithQuestionsDTO StartTakingTheQuiz(Long classroomId,Long quizId);

    ResponseEntity<String> submitSolution(Long quizId, QuizAnswerSubmitDTO quizAnswerRequestDTO);

    ResponseEntity<String> submitSingleQuestion(Long quizId, Long questionId, QuestionAnswerSubmitDTO questionAnswerSubmitDTO);
}