package com.tawfeek.quizApi.controller;


import com.tawfeek.quizApi.model.quiz.QuizResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classrooms/quizzes")
public class QuizController {


    // Create quiz
    @PostMapping
    public void createQuiz() {
        // Implementation goes here
    }

    // Get classroom quizzes
    @GetMapping
    public List<QuizResponseDTO> getClassroomQuizzes() {
        return null; // Placeholder, replace with actual implementation
    }

    // Delete quiz
    @DeleteMapping("/{quizId}")
    public void deleteQuiz(@PathVariable Long quizId) {
        // Implementation goes here
    }

    // Take quiz
    @PostMapping("/{quizId}/take")
    public void takeQuiz(@PathVariable Long quizId) {
        // Implementation goes here
    }

    /** Submit result and images
     * if you exceed the exam time the response status will be 422 with message "the submission time is exceeded"
     * can submit many times during exam until he sends end the exam
     * will submit images also screenshot and image of the person solving the exam
     */
    @PostMapping("/{quizId}/submit")
    public void submitResultAndImages(@PathVariable Long quizId) {
        // Implementation goes here
    }


    /** End the exam
     * end the exam for student will submit and end
     * user can't submit after he end the exam
     */
    @PostMapping("/{quizId}/end")
    public void endExam(@PathVariable Long quizId) {
        // Implementation goes here
    }

    // Calculate exam results or recalculate them

    /**
     * endpoint that will calculate exam results and return it back to the user
     */
    @PostMapping("/{quizId}/calculate-results")
    public void calculateExamResults(@PathVariable Long quizId) {
        // Implementation goes here
    }



}
