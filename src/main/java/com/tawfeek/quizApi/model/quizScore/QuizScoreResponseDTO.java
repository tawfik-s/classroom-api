package com.tawfeek.quizApi.model.quizScore;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tawfeek.quizApi.entity.User;
import com.tawfeek.quizApi.model.user.UserResponseDTO;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizScoreResponseDTO {

    private Long id;

    private UserResponseDTO userResponseDTO;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date submtionDate;

    private Long Score;

}
