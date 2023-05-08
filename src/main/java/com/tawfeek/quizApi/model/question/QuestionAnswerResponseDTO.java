package com.tawfeek.quizApi.model.question;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswerResponseDTO {

    private Long id;

    private String answer;

}
