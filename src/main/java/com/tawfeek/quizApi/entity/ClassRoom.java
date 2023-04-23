package com.tawfeek.quizApi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "class_rooms")
public class ClassRoom {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany
    private List<Quiz> quizzes;

    @OneToMany
    private List<User> members;


    public ClassRoom(String name) {
        this.name=name;
    }
}
