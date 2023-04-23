package com.tawfeek.quizApi.service;

import com.tawfeek.quizApi.model.user.UserResponseDTO;

import java.util.List;

public interface UserService {

    public UserResponseDTO getUser(Long id);

    public List<UserResponseDTO> findUsersByName(String name);

    public UserResponseDTO findUserByEmail(String email);

}
