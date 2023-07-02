package com.tawfeek.quizApi.model.reports;

import com.tawfeek.quizApi.model.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResultReport {

    private UserResponseDTO userResponseDTO;

    private Long numberOfQuestions;

    private Long numberOfRightQuestion;
}
