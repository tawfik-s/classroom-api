package com.tawfeek.quizApi.model.quiz;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResponseDTO {

    private Long id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date creationDateTime;

    private Long Duration;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date closeDate;


}
