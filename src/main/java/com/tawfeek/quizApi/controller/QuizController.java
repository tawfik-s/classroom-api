package com.tawfeek.quizApi.controller;


import com.tawfeek.quizApi.model.quiz.QuizRequestDTO;
import com.tawfeek.quizApi.model.quiz.QuizResponseDTO;
import com.tawfeek.quizApi.model.quiz.QuizResponseWithQuestionsDTO;
import com.tawfeek.quizApi.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classrooms")
public class QuizController {


    // Create quiz

    @Autowired
    private QuizService quizService;

    /**
     * add quiz to classroom by admin
     *
     * @param quizRequestDTO
     * @return QuizResponseDTO
     */
    @PostMapping("/{classroomId}/quizzes")
    public QuizResponseDTO createQuiz(@PathVariable Long classroomId,@RequestBody QuizRequestDTO quizRequestDTO){
        return quizService.createQuiz(classroomId,quizRequestDTO);
    }


    // Get classroom quizzes
    @GetMapping("/quizzes")
    public List<QuizResponseDTO> getClassroomQuizzes() {
        return null; // Placeholder, replace with actual implementation
    }

    // Delete quiz
    @DeleteMapping("/quizzes/{quizId}")
    public void deleteQuiz(@PathVariable Long quizId) {
        // Implementation goes here
    }

    // Take quiz
    @PostMapping("/quizzes/{quizId}/take")
    public QuizResponseWithQuestionsDTO takeQuiz(@PathVariable Long quizId) {
        return null;
    }

    /** Submit result and images
     * if you exceed the exam time the response status will be 422 with message "the submission time is exceeded"
     * can submit many times during exam until he sends end the exam
     * will submit images also screenshot and image of the person solving the exam
     */
    @PostMapping("/quizzes/{quizId}/submit")
    public void submitResultAndImages(@PathVariable Long quizId) {
        // Implementation goes here
    }

    //todo submit single question answer

    @PostMapping("/quizzes/{quizId}/submit/{questionId}")
    public void submitQuestionAnswer(@PathVariable Long quizId,@PathVariable Long questionId){

    }


    /** End the exam
     * end the exam for student will submit and end
     * user can't submit after he end the exam
     */
    @PostMapping("/quizzes/{quizId}/end")
    public void endExam(@PathVariable Long quizId) {
        // Implementation goes here
    }

    // Calculate exam results or recalculate them

    /**
     * endpoint that will calculate exam results and return it back to the user
     */
    @PostMapping("/quizzes/{quizId}/calculate-results")
    public void calculateExamResults(@PathVariable Long quizId) {
        // Implementation goes here
    }



}
