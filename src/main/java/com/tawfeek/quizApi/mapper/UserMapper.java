package com.tawfeek.quizApi.mapper;

import com.tawfeek.quizApi.entity.User;
import com.tawfeek.quizApi.model.user.UserRequestDTO;
import com.tawfeek.quizApi.model.user.UserResponseDTO;

public interface UserMapper {

    public User toEntity(UserRequestDTO userRequestDTO);

    public UserResponseDTO toDTO(User user);
}
