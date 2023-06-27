package com.tawfeek.quizApi.service.Impl;

import com.tawfeek.quizApi.Utils.SecurityUtils;
import com.tawfeek.quizApi.entity.*;
import com.tawfeek.quizApi.mapper.AdminQuestionMapper;
import com.tawfeek.quizApi.mapper.AdminQuizMapper;
import com.tawfeek.quizApi.mapper.QuizMapper;
import com.tawfeek.quizApi.model.question.AdminQuestionRequestDTO;
import com.tawfeek.quizApi.model.quiz.*;
import com.tawfeek.quizApi.repository.*;
import com.tawfeek.quizApi.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

  @Autowired private UserRepository userRepository;

  @Autowired private ClassRoomRepository classRoomRepository;

  @Autowired private QuizRepository quizRepository;

  @Autowired private QuizAnswerRepository quizAnswerRepository;

  @Autowired private QuestionRepository questionRepository;

  @Autowired private QuestionAnswerRepository questionAnswerRepository;

  @Autowired private QuizMapper quizMapper;

  @Autowired private AdminQuestionMapper adminQuestionMapper;

  @Autowired private AdminQuizMapper adminQuizMapper;

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
    questionRepository.saveAll(questions);
    Quiz quiz = adminQuizMapper.toEntity(quizRequest, questions);
    quizRepository.save(quiz);
    return adminQuizMapper.toDTO(quiz);
  }

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
}
