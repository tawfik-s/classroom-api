package com.tawfeek.quizApi.repository;

import com.tawfeek.quizApi.entity.QuizScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizScoreRepository extends JpaRepository<QuizScore,Long> {
}
