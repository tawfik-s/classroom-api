package com.tawfeek.quizApi.service.Impl;

import com.tawfeek.quizApi.Utils.SecurityUtils;
import com.tawfeek.quizApi.entity.ClassRoom;
import com.tawfeek.quizApi.entity.Quiz;
import com.tawfeek.quizApi.entity.QuizAnswer;
import com.tawfeek.quizApi.entity.User;
import com.tawfeek.quizApi.mapper.*;
import com.tawfeek.quizApi.model.quiz.QuizResponseWithQuestionsDTO;
import com.tawfeek.quizApi.repository.*;
import com.tawfeek.quizApi.service.MemberQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import static com.tawfeek.quizApi.Utils.TimeUtils.validateAvailableTimeToContinueSolving;
import static com.tawfeek.quizApi.Utils.TimeUtils.validateQuizTakingOrSubmitTime;

@Service
public class MemberQuizServiceImpl implements MemberQuizService {

  @Autowired private UserRepository userRepository;

  @Autowired private ClassRoomRepository classRoomRepository;

  @Autowired private QuizRepository quizRepository;

  @Autowired private QuizAnswerRepository quizAnswerRepository;

  @Autowired private QuestionRepository questionRepository;

  @Autowired private QuestionAnswerRepository questionAnswerRepository;

  @Autowired private QuizMapper quizMapper;

  @Autowired private AdminQuestionMapper adminQuestionMapper;

  @Autowired private AdminQuizMapper adminQuizMapper;

  @Autowired private QuizWithQuestionMapper quizWithQuestionMapper;

  @Autowired private QuestionMapper questionMapper;

  @Override
  public QuizResponseWithQuestionsDTO StartTakingTheQuiz(Long classroomId, Long quizId) {
    String userEmail = SecurityUtils.getCurrentUserEmail();
    User user = userRepository.findByEmail(userEmail).orElseThrow();
    if (!classRoomRepository.isUserAdminOfClassRoom(classroomId, user.getId())
        && !classRoomRepository.isUserMemberOfClassRoom(classroomId, user.getId())) {
      throw new ResponseStatusException(
          HttpStatus.FORBIDDEN, "you are not authorized to take the exam");
    }
    if (!quizRepository.isQuizInsideClassRoom(classroomId, quizId)) {
      throw new ResponseStatusException(
          HttpStatus.FORBIDDEN, "there is no quiz in the classroom with this id");
    }
    Quiz quiz = quizRepository.findById(quizId).orElseThrow();
    validateQuizTakingOrSubmitTime(quiz);
    if (quizAnswerRepository.isQuizTakenBefore(quizId, user.getId())) {
      QuizAnswer quizAnswer = quizAnswerRepository.findQuizAnswerByQuizAndUser(quiz, user);
      if (quizAnswer.getFinish()) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "you finished your quiz");
      }
      validateAvailableTimeToContinueSolving(quiz, quizAnswer);
      return quizWithQuestionMapper.toDTO(quiz, quizAnswer);
    } else {
      QuizAnswer quizAnswer = new QuizAnswer();
      quizAnswer.setQuiz(quiz);
      quizAnswer.setUser(user);
      quizAnswer.setStartTime(Calendar.getInstance().getTime());
      quizAnswer.setFinish(false);
      quizAnswerRepository.save(quizAnswer);
      return quizWithQuestionMapper.toDTO(quiz);
    }
  }
}
