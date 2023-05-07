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
@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDateTime;

    private Long Duration;

    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDate;

    @OneToMany(cascade = CascadeType.ALL)
    private List<QuizScore> quizScores;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions;
}
