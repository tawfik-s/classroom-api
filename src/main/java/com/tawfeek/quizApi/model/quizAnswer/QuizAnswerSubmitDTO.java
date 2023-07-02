package com.tawfeek.quizApi.model.quizAnswer;


import com.tawfeek.quizApi.model.questionAnswer.QuestionAnswerSubmitDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizAnswerSubmitDTO {
    private List<QuestionAnswerSubmitDTO> questionsAnswerList;
}
