package com.tawfeek.quizApi.controller;

import com.tawfeek.quizApi.model.quiz.QuizResponseWithQuestionsDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/classrooms")
public class UserQuizController {

    // Take quiz
    @PostMapping("/quizzes/{quizId}/take")
    public QuizResponseWithQuestionsDTO takeQuiz(@PathVariable Long quizId) {
        return null;
    }

    /**
     * Submit result and images if you exceed the exam time the response status will be 422 with
     * message "the submission time is exceeded" can submit many times during exam until he sends end
     * the exam will submit images also screenshot and image of the person solving the exam
     */
    @PostMapping("/quizzes/{quizId}/submit")
    public void submitResultAndImages(@PathVariable Long quizId) {
        // Implementation goes here
    }

    // todo submit single question answer

    @PostMapping("/quizzes/{quizId}/submit/{questionId}")
    public void submitQuestionAnswer(@PathVariable Long quizId, @PathVariable Long questionId) {}

    /**
     * End the exam end the exam for student will submit and end user can't submit after he end the
     * exam
     */
    @PostMapping("/quizzes/{quizId}/end")
    public void endExam(@PathVariable Long quizId) {
        // Implementation goes here
    }
    // Calculate exam results or recalculate them

    /** endpoint that will calculate exam results and return it back to the user */
    @PostMapping("/quizzes/{quizId}/calculate-results")
    public void calculateExamResults(@PathVariable Long quizId) {
        // Implementation goes here
    }
}
