package com.tawfeek.quizApi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quiz_answers")
public class QuizAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date submtionDate;

    @ManyToOne
    private Quiz quiz;

    @OneToMany
    private List<QuestionAnswer> questionAnswers;

}
