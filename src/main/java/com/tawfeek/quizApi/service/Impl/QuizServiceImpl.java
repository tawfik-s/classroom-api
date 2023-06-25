package com.tawfeek.quizApi.service.Impl;

import com.tawfeek.quizApi.Utils.SecurityUtils;
import com.tawfeek.quizApi.entity.ClassRoom;
import com.tawfeek.quizApi.entity.Quiz;
import com.tawfeek.quizApi.entity.QuizAnswer;
import com.tawfeek.quizApi.entity.User;
import com.tawfeek.quizApi.mapper.QuizMapper;
import com.tawfeek.quizApi.model.quiz.QuizRequestDTO;
import com.tawfeek.quizApi.model.quiz.QuizResponseDTO;
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
import java.util.Date;
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
}
