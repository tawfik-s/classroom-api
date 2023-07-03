package com.tawfeek.quizApi.service.Impl;

import com.tawfeek.quizApi.Utils.SecurityUtils;
import com.tawfeek.quizApi.entity.*;
import com.tawfeek.quizApi.mapper.AdminQuestionMapper;
import com.tawfeek.quizApi.mapper.AdminQuizMapper;
import com.tawfeek.quizApi.mapper.QuizMapper;
import com.tawfeek.quizApi.mapper.UserMapper;
import com.tawfeek.quizApi.model.question.AdminQuestionRequestDTO;
import com.tawfeek.quizApi.model.quiz.*;
import com.tawfeek.quizApi.model.reports.QuizResultReport;
import com.tawfeek.quizApi.repository.*;
import com.tawfeek.quizApi.service.AdminQuizService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminQuizServiceImpl implements AdminQuizService {

  @Autowired private UserRepository userRepository;

  @Autowired private ClassRoomRepository classRoomRepository;

  @Autowired private QuizRepository quizRepository;

  @Autowired private QuizAnswerRepository quizAnswerRepository;

  @Autowired private QuestionRepository questionRepository;

  @Autowired private QuestionAnswerRepository questionAnswerRepository;

  @Autowired private QuizMapper quizMapper;

  @Autowired private AdminQuestionMapper adminQuestionMapper;

  @Autowired private AdminQuizMapper adminQuizMapper;

  @Autowired private UserMapper userMapper;

  @Override
  public QuizResponseDTO createQuiz(Long classroomId, QuizRequestDTO quizRequestDTO) {
    String currentUserEmail = SecurityUtils.getCurrentUserEmail();
    User currentUser = userRepository.findByEmail(currentUserEmail).orElseThrow();
    ClassRoom classRoom = classRoomRepository.findById(classroomId).orElseThrow();
    if (!classRoomRepository.isUserAdminOfClassRoom(classroomId, currentUser.getId())) {
      throw new ResponseStatusException(
          HttpStatus.FORBIDDEN,
          "You are not authorized to add a quiz. Only admins can perform this action.");
    }
    if (quizRequestDTO.getDuration() < 0) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Duration should be greater than 0");
    }
    Long durationInMinutes = quizRequestDTO.getDuration();
    LocalDateTime creationDateTime =
        LocalDateTime.ofInstant(quizRequestDTO.getCreationDateTime().toInstant(), ZoneOffset.UTC);
    LocalDateTime closeDateTime =
        LocalDateTime.ofInstant(quizRequestDTO.getCloseDate().toInstant(), ZoneOffset.UTC);
    LocalDateTime closingDateTime = creationDateTime.plus(durationInMinutes, ChronoUnit.MINUTES);
    if (closingDateTime.isAfter(closeDateTime)) {
      throw new IllegalArgumentException(
          "The closing date and time exceeds the provided close date.");
    }
    Quiz quiz = quizMapper.toEntity(quizRequestDTO);
    quizRepository.save(quiz);
    classRoom.getQuizzes().add(quiz);
    classRoomRepository.save(classRoom);
    return quizMapper.toDTO(quiz);
  }

  @Override
  @Transactional
  public AdminQuizWithQuestionsResponseDTO createQuizWithQuestions(
      Long classroomId, AdminQuizWithQuestionRequestDTO quizRequest) {
    User currentUser =
        userRepository.findByEmail(SecurityUtils.getCurrentUserEmail()).orElseThrow();

    ClassRoom classRoom = classRoomRepository.findById(classroomId).orElseThrow();
    if (!classRoomRepository.isUserAdminOfClassRoom(classroomId, currentUser.getId())) {
      throw new ResponseStatusException(
          HttpStatus.FORBIDDEN,
          "You are not authorized to add a quiz. Only admins can perform this action.");
    }
    if (quizRequest.getDuration() < 0) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Duration should be greater than 0");
    }
    Long durationInMinutes = quizRequest.getDuration();
    LocalDateTime creationDateTime =
        LocalDateTime.ofInstant(quizRequest.getCreationDateTime().toInstant(), ZoneOffset.UTC);
    LocalDateTime closeDateTime =
        LocalDateTime.ofInstant(quizRequest.getCloseDate().toInstant(), ZoneOffset.UTC);
    LocalDateTime closingDateTime = creationDateTime.plus(durationInMinutes, ChronoUnit.MINUTES);
    if (closingDateTime.isAfter(closeDateTime)) {
      throw new IllegalArgumentException(
          "The closing date and time exceeds the provided close date.");
    }
    List<Question> questions =
        quizRequest.getQuestions().stream()
            .map(question -> adminQuestionMapper.toEntity(question))
            .toList();
    questions = questionRepository.saveAll(questions);
    Quiz quiz = adminQuizMapper.toEntity(quizRequest);
    quiz.setQuestions(questions);
    quizRepository.save(quiz);
    classRoom.getQuizzes().add(quiz);
    classRoomRepository.save(classRoom);
    return adminQuizMapper.toDTO(quiz);
  }

  @Override
  @Transactional
  public void deleteQuiz(Long classroomId, Long quizId) {
    User currentUser =
        userRepository
            .findByEmail(SecurityUtils.getCurrentUserEmail())
            .orElseThrow(() -> new AuthorizationServiceException("Current user not found"));

    ClassRoom classRoom =
        classRoomRepository
            .findById(classroomId)
            .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));

    if (!classRoomRepository.isUserAdminOfClassRoom(classroomId, currentUser.getId())) {
      throw new AuthorizationServiceException(
          "You are not the admin of the classroom to delete quiz");
    }

    Quiz quiz =
        quizRepository
            .findById(quizId)
            .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

    if (!classRoom.getQuizzes().contains(quiz)) {
      throw new IllegalArgumentException("The quiz does not belong to the specified classroom");
    }

    classRoom.getQuizzes().remove(quiz); // Remove quiz from classroom
    quizRepository.delete(quiz); // Delete the quiz from the database
  }

  @Override
  public AdminQuizWithQuestionsResponseDTO getQuizWithQuestionsById(Long classroomId, Long quizId) {
    User currentUser =
        userRepository.findByEmail(SecurityUtils.getCurrentUserEmail()).orElseThrow();
    if (!classRoomRepository.isUserAdminOfClassRoom(classroomId, currentUser.getId())) {
      throw new AuthorizationServiceException(
          "you are not the admin of the classroom to add question");
    }
    Quiz quiz = quizRepository.findById(quizId).orElseThrow();
    return adminQuizMapper.toDTO(quiz);
  }

  @Override
  @Transactional
  public AdminQuizWithQuestionsResponseDTO updateQuiz(
      Long classroomId, Long quizId, QuizRequestDTO quizRequestDTO) {
    User currentUser =
        userRepository.findByEmail(SecurityUtils.getCurrentUserEmail()).orElseThrow();
    if (!classRoomRepository.isUserAdminOfClassRoom(classroomId, currentUser.getId())) {
      throw new AuthorizationServiceException(
          "you are not the admin of the classroom to add question");
    }
    if (quizRequestDTO.getDuration() < 0) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Duration should be greater than 0");
    }
    Long durationInMinutes = quizRequestDTO.getDuration();
    LocalDateTime creationDateTime =
        LocalDateTime.ofInstant(quizRequestDTO.getCreationDateTime().toInstant(), ZoneOffset.UTC);
    LocalDateTime closeDateTime =
        LocalDateTime.ofInstant(quizRequestDTO.getCloseDate().toInstant(), ZoneOffset.UTC);
    LocalDateTime closingDateTime = creationDateTime.plus(durationInMinutes, ChronoUnit.MINUTES);
    if (closingDateTime.isAfter(closeDateTime)) {
      throw new IllegalArgumentException(
          "The closing date and time exceeds the provided close date.");
    }
    ClassRoom classRoom = classRoomRepository.findById(classroomId).orElseThrow();
    // todo need to be optimized with jpql
    Quiz quiz =
        classRoom.getQuizzes().stream()
            .filter(q -> q.getId().equals(quizId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));
    quiz.setName(quizRequestDTO.getName());
    quiz.setDuration(quizRequestDTO.getDuration());
    quiz.setCreationDateTime(quizRequestDTO.getCreationDateTime());
    quiz.setCloseDate(quizRequestDTO.getCloseDate());
    quizRepository.save(quiz);
    return adminQuizMapper.toDTO(quiz);
  }

  @Override
  @Transactional
  public AdminQuizWithQuestionsResponseDTO updateQuestion(
      Long classroomId,
      Long quizId,
      Long questionId,
      AdminQuestionRequestDTO adminQuestionRequestDTO) {
    User currentUser =
        userRepository.findByEmail(SecurityUtils.getCurrentUserEmail()).orElseThrow();
    if (!classRoomRepository.isUserAdminOfClassRoom(classroomId, currentUser.getId())) {
      throw new AuthorizationServiceException(
          "you are not the admin of the classroom to add question");
    }
    ClassRoom classRoom = classRoomRepository.findById(classroomId).orElseThrow();
    // todo need to be optimized with jpql
    Quiz quiz =
        classRoom.getQuizzes().stream()
            .filter(q -> q.getId().equals(quizId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));
    Question question =
        quiz.getQuestions().stream()
            .filter(q -> q.getId().equals(questionId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Question not found"));

    question.setText(adminQuestionRequestDTO.getText());
    question.setOptionA(adminQuestionRequestDTO.getOptionA());
    question.setOptionB(adminQuestionRequestDTO.getOptionB());
    question.setOptionC(adminQuestionRequestDTO.getOptionC());
    question.setOptionD(adminQuestionRequestDTO.getOptionD());
    question.setCorrectAnswers(adminQuestionRequestDTO.getCorrectAnswer());
    questionRepository.save(question);
    return adminQuizMapper.toDTO(quiz);
  }

  @Override
  public void deleteQuestion(Long classroomId, Long quizId, Long questionId) {
    User currentUser =
        userRepository
            .findByEmail(SecurityUtils.getCurrentUserEmail())
            .orElseThrow(() -> new AuthorizationServiceException("Current user not found"));
    ClassRoom classRoom =
        classRoomRepository
            .findById(classroomId)
            .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
    if (!classRoomRepository.isUserAdminOfClassRoom(classroomId, currentUser.getId())) {
      throw new AuthorizationServiceException("You are not the admin of the classroom");
    }
    Quiz quiz =
        classRoom.getQuizzes().stream()
            .filter(q -> q.getId().equals(quizId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));
    Question question =
        quiz.getQuestions().stream()
            .filter(q -> q.getId().equals(questionId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Question not found"));
    quiz.getQuestions().remove(question); // Remove question from quiz
    questionRepository.delete(question); // Delete the question from the database
  }

  @Override
  public List<QuizResponseDTO> getClassroomQuizzes(Long classroomId) {
    String currentUserEmail = SecurityUtils.getCurrentUserEmail();
    User currentUser = userRepository.findByEmail(currentUserEmail).orElseThrow();
    if (!classRoomRepository.isUserMemberOfClassRoom(classroomId, currentUser.getId())
        && !classRoomRepository.isUserAdminOfClassRoom(classroomId, currentUser.getId())) {
      throw new AuthorizationServiceException("you are not authorized to access this classroom");
    }
    return classRoomRepository.findById(classroomId).orElseThrow().getQuizzes().stream()
        .map((quiz -> quizMapper.toDTO(quiz)))
        .toList();
  }

  @Override
  public AdminQuizWithQuestionsResponseDTO addQuestion(
      long classroomId, long quizId, AdminQuestionRequestDTO questionRequest) {
    User currentUser =
        userRepository.findByEmail(SecurityUtils.getCurrentUserEmail()).orElseThrow();
    if (!classRoomRepository.isUserAdminOfClassRoom(classroomId, currentUser.getId())) {
      throw new AuthorizationServiceException(
          "you are not the admin of the classroom to add question");
    }
    Quiz quiz = quizRepository.findById(quizId).orElseThrow();
    Question question = adminQuestionMapper.toEntity(questionRequest);
    questionRepository.save(question);
    quiz.getQuestions().add(question);
    quizRepository.save(quiz);
    return adminQuizMapper.toDTO(quiz);
  }

  @Override
  public List<QuizResultReport> calculateQuizResult(Long classroomId, Long quizId) {
    User currentUser =
        userRepository.findByEmail(SecurityUtils.getCurrentUserEmail()).orElseThrow();
    if (!classRoomRepository.isUserAdminOfClassRoom(classroomId, currentUser.getId())) {
      throw new AuthorizationServiceException(
          "you are not the admin of the classroom calculate results");
    }
    if (!quizRepository.isQuizInsideClassRoom(classroomId, quizId)) {
      throw new ResponseStatusException(
          HttpStatus.FORBIDDEN, "quiz is not in the classroom you owned");
    }
    Quiz quiz = quizRepository.findById(quizId).orElseThrow();
    List<QuizResultReport> res = new ArrayList<>();
    List<QuizAnswer> quizAnswers = quiz.getQuizAnswers();

    for (var quizAnswer : quizAnswers) {
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
      res.add(quizResultReport);
    }
    return res;
  }
}
