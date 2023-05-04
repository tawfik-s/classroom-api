package com.tawfeek.quizApi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @OneToMany(cascade = CascadeType.ALL)
    private List<Quiz> quizzes;

    @OneToMany(cascade = CascadeType.ALL)
    private List<User> members;


    public ClassRoom(String name) {
        this.name=name;
    }

    public void addMember(User user){
        if(members==null){
            members=new ArrayList<>();
        }
        members.add(user);
    }
}
