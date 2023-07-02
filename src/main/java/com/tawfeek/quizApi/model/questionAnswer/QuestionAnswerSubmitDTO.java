package com.tawfeek.quizApi.model.questionAnswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswerSubmitDTO {
    private long id;
    private String answer;
}
