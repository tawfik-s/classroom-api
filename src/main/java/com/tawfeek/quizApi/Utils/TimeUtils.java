package com.tawfeek.quizApi.Utils;

import com.tawfeek.quizApi.entity.Quiz;
import com.tawfeek.quizApi.entity.QuizAnswer;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class TimeUtils {


    public static void validateAvailableTimeToContinueSolving(Quiz quiz, QuizAnswer quizAnswer) {
        Long durationInMinutes = quiz.getDuration();
        LocalDateTime startDateTime =
                LocalDateTime.ofInstant(quizAnswer.getStartTime().toInstant(), ZoneOffset.UTC);

        LocalDateTime endDateTime = startDateTime.plus(durationInMinutes, ChronoUnit.MINUTES);
        if(endDateTime.isAfter(LocalDateTime.now())){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "the quiz is not Available your duration is finished");
        }
    }

    public static void validateQuizTakingOrSubmitTime(Quiz quiz) {
        LocalDateTime creationDateTime =
                LocalDateTime.ofInstant(quiz.getCreationDateTime().toInstant(), ZoneOffset.UTC);
        LocalDateTime closeDateTime =
                LocalDateTime.ofInstant(quiz.getCloseDate().toInstant(), ZoneOffset.UTC);
        // validate the time of the quiz
        if(closeDateTime.isBefore(LocalDateTime.now())){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "the quiz is currently not available time is finished");
        }
        if(creationDateTime.isAfter(LocalDateTime.now())){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "the quiz will be available at "+creationDateTime);
        }
    }
}
