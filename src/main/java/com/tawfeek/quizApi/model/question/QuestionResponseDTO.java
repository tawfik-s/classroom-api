package com.tawfeek.quizApi.model.question;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionResponseDTO {
    private Long id;

    private String text;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;
}
