package com.tawfeek.quizApi.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class QuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Question question;

    private String answer;

}
