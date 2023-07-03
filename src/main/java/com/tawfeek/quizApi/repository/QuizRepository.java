package com.tawfeek.quizApi.repository;


import com.tawfeek.quizApi.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {

    @Query(
            "SELECT COUNT(q)>0 FROM ClassRoom c join c.quizzes q "
                    + "where q.id = :quizId and c.id = :classroomId")
    boolean isQuizInsideClassRoom(
            @Param("classroomId") Long classroomId, @Param("quizId") Long quizId);


}
