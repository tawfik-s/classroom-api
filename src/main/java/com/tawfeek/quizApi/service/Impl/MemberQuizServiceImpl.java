package com.tawfeek.quizApi.service.Impl;

import com.tawfeek.quizApi.Utils.SecurityUtils;
import com.tawfeek.quizApi.entity.*;
import com.tawfeek.quizApi.mapper.*;
import com.tawfeek.quizApi.model.questionAnswer.QuestionAnswerSubmitDTO;
import com.tawfeek.quizApi.model.quiz.QuizResponseWithQuestionsDTO;
import com.tawfeek.quizApi.model.quizAnswer.QuizAnswerSubmitDTO;
import com.tawfeek.quizApi.model.reports.QuizResultReport;
import com.tawfeek.quizApi.repository.*;
import com.tawfeek.quizApi.service.MemberQuizService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

  @Autowired private UserMapper userMapper;

  @Override
  @Transactional
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
      quizAnswer = quizAnswerRepository.save(quizAnswer);
      quiz.getQuizAnswers().add(quizAnswer);
      quiz = quizRepository.save(quiz);
      return quizWithQuestionMapper.toDTO(quiz);
    }
  }

  @Override
  @Transactional
  public ResponseEntity<String> submitSolution(
      Long quizId, QuizAnswerSubmitDTO quizAnswerRequestDTO) {
    String userEmail = SecurityUtils.getCurrentUserEmail();
    User user = userRepository.findByEmail(userEmail).orElseThrow();
    Quiz quiz = quizRepository.findById(quizId).orElseThrow();
    validateQuizTakingOrSubmitTime(quiz);
    if (!quizAnswerRepository.isQuizTakenBefore(quizId, user.getId())) {
      throw new ResponseStatusException(
          HttpStatus.NOT_ACCEPTABLE, "you should take the quiz first to start submit answers");
    }
    QuizAnswer quizAnswer = quizAnswerRepository.findQuizAnswerByQuizAndUser(quiz, user);
    if (quizAnswer.getFinish()) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "you finished your quiz");
    }
    validateAvailableTimeToContinueSolving(quiz, quizAnswer);
    questionAnswerRepository.deleteAll(quizAnswer.getQuestionAnswers());
    List<QuestionAnswer> questionAnswers = new ArrayList<>();
    for (var answer : quizAnswerRequestDTO.getQuestionsAnswerList()) {
      Question question = questionRepository.findById(answer.getId()).orElseThrow();
      questionAnswers.add(new QuestionAnswer(question, answer.getAnswer()));
    }
    questionAnswers = questionAnswerRepository.saveAll(questionAnswers);
    quizAnswer.setQuestionAnswers(questionAnswers);
    quizAnswerRepository.save(quizAnswer);
    return new ResponseEntity<>("submit succeed", HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity<String> submitSingleQuestion(
      Long quizId, Long questionId, QuestionAnswerSubmitDTO questionAnswerSubmitDTO) {
    String userEmail = SecurityUtils.getCurrentUserEmail();
    User user = userRepository.findByEmail(userEmail).orElseThrow();
    Quiz quiz = quizRepository.findById(quizId).orElseThrow();
    validateQuizTakingOrSubmitTime(quiz);
    if (!quizAnswerRepository.isQuizTakenBefore(quizId, user.getId())) {
      throw new ResponseStatusException(
          HttpStatus.NOT_ACCEPTABLE, "you should take the quize first to start submit answers");
    }
    QuizAnswer quizAnswer = quizAnswerRepository.findQuizAnswerByQuizAndUser(quiz, user);
    if (quizAnswer.getFinish()) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "you finished your quiz");
    }
    validateAvailableTimeToContinueSolving(quiz, quizAnswer);

    var questionAnswer =
        quizAnswer.getQuestionAnswers().stream()
            .filter(x -> x.getQuestion().getId().equals(questionId))
            .findFirst();
    if (questionAnswer.isPresent()) {
      QuestionAnswer questionAnswer1 = questionAnswer.get();
      questionAnswer1.setAnswer(questionAnswerSubmitDTO.getAnswer());
      questionAnswerRepository.save(questionAnswer1);
      return new ResponseEntity<>("submit succeed", HttpStatus.OK);
    }
    QuestionAnswer questionAnswer1 = new QuestionAnswer();
    questionAnswer1.setQuestion(questionRepository.findById(questionId).orElseThrow());
    questionAnswer1.setAnswer(questionAnswerSubmitDTO.getAnswer());
    questionAnswer1 = questionAnswerRepository.save(questionAnswer1);
    quizAnswer.getQuestionAnswers().add(questionAnswer1);
    quizAnswerRepository.save(quizAnswer);

    return new ResponseEntity<>("submit succeed", HttpStatus.OK);
  }

  @Override
  public QuizResultReport calculateMyQuizResult(Long quizId) {

    User currentUser =
        userRepository.findByEmail(SecurityUtils.getCurrentUserEmail()).orElseThrow();

    if (!quizAnswerRepository.isQuizTakenBefore(quizId, currentUser.getId())) {
      throw new ResponseStatusException(
          HttpStatus.NOT_ACCEPTABLE,
          "you should take the quiz first and time finish to get the result");
    }
    Quiz quiz = quizRepository.findById(quizId).orElseThrow();
    LocalDateTime closeDateTime =
        LocalDateTime.ofInstant(quiz.getCloseDate().toInstant(), ZoneOffset.UTC);
    if (closeDateTime.isAfter(LocalDateTime.now())) {
      throw new ResponseStatusException(
          HttpStatus.SERVICE_UNAVAILABLE, "the quiz results not available until quiz closed");
    }
    QuizAnswer quizAnswer = quizAnswerRepository.findQuizAnswerByQuizAndUser(quiz, currentUser);
    int score = 0;
    for (var questionAnswer : quizAnswer.getQuestionAnswers()) {
      if (questionAnswer.getAnswer().equals(questionAnswer.getQuestion().getCorrectAnswers())) {
        score++;
      }
    }
    QuizResultReport quizResultReport = new QuizResultReport();
    quizResultReport.setUserResponseDTO(userMapper.toDTO(currentUser));
    quizResultReport.setNumberOfQuestions((long) quiz.getQuestions().size());
    quizResultReport.setNumberOfRightQuestion((long) score);
    return quizResultReport;
  }

  @Override
  public ResponseEntity<String> endTheQuiz(Long quizId, QuizAnswerSubmitDTO quizAnswerRequestDTO) {
    submitSolution(quizId, quizAnswerRequestDTO);
    User currentUser =
        userRepository.findByEmail(SecurityUtils.getCurrentUserEmail()).orElseThrow();
    Quiz quiz = quizRepository.findById(quizId).orElseThrow();
    QuizAnswer quizAnswer = quizAnswerRepository.findQuizAnswerByQuizAndUser(quiz, currentUser);
    quizAnswer.setFinish(true);
    quizAnswerRepository.save(quizAnswer);
    return new ResponseEntity("done ", HttpStatus.OK);
  }
}
