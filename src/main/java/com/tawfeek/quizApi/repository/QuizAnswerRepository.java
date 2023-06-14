package com.tawfeek.quizApi.repository;

import com.tawfeek.quizApi.entity.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizAnswerRepository extends JpaRepository<QuizAnswer,Long> {
}
