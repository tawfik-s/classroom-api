package com.tawfeek.quizApi.repository;

import com.tawfeek.quizApi.entity.Quiz;
import com.tawfeek.quizApi.entity.QuizAnswer;
import com.tawfeek.quizApi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizAnswerRepository extends JpaRepository<QuizAnswer,Long> {

    @Query("SELECT COUNT(qa)>0 FROM QuizAnswer qa " +
            "WHERE qa.user.id = :userId and qa.quiz.id= :quizId")
    boolean isQuizTakenBefore(@Param("quizId") Long quizId,
                              @Param("userId") Long userId);

    QuizAnswer findQuizAnswerByQuizAndUser(Quiz quiz, User user);
}
