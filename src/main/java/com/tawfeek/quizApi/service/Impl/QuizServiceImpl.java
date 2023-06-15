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
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizAnswerRepository quizAnswerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    private QuizMapper quizMapper;


    public QuizResponseDTO createQuiz(Long classroomId, QuizRequestDTO quizRequestDTO) {
        String currentUserEmail = SecurityUtils.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow();
        ClassRoom classRoom = classRoomRepository.findById(classroomId)
                .orElseThrow();

        if (!classRoomRepository.isUserAdminOfClassRoom(classroomId, currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN
                    , "You are not authorized to add a quiz. Only admins can perform this action.");
        }
        //add quiz date validation

        Quiz quiz = quizMapper.toEntity(quizRequestDTO);
        quizRepository.save(quiz);
        classRoom.getQuizzes().add(quiz);
        classRoomRepository.save(classRoom);

        return quizMapper.toDTO(quiz);
    }

}
