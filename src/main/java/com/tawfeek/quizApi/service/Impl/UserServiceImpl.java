package com.tawfeek.quizApi.service.Impl;

import com.tawfeek.quizApi.entity.User;
import com.tawfeek.quizApi.exception.RecordNotFoundException;
import com.tawfeek.quizApi.mapper.UserMapper;
import com.tawfeek.quizApi.model.user.UserResponseDTO;
import com.tawfeek.quizApi.repository.UserRepository;
import com.tawfeek.quizApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResponseDTO getUser(Long id) {
       User user= userRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        return userMapper.toDTO(user);
    }

    @Override
    public List<UserResponseDTO> findUsersByName(String name) {
        List<User> users = userRepository.findByUserName(name).orElseThrow(RecordNotFoundException::new);
        return users.stream().map(user->userMapper.toDTO(user)).toList();
    }

    @Override
    public UserResponseDTO findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(RecordNotFoundException::new);
        return userMapper.toDTO(user);
    }
}
