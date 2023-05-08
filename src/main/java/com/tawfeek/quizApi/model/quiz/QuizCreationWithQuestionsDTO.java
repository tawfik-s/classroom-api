package com.tawfeek.quizApi.model.quiz;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tawfeek.quizApi.model.question.AdminQuestionRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizCreationWithQuestionsDTO {

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date creationDateTime;

    private Long Duration;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date closeDate;

    private List<AdminQuestionRequestDTO> questions;

}
