package com.tawfeek.quizApi.mapper.Impl;


import com.tawfeek.quizApi.entity.User;
import com.tawfeek.quizApi.mapper.UserMapper;
import com.tawfeek.quizApi.model.Role;
import com.tawfeek.quizApi.model.user.UserRequestDTO;
import com.tawfeek.quizApi.model.user.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public  User toEntity(UserRequestDTO userRequestDTO) {
        User newUser = new User();
        newUser.setUserName(userRequestDTO.getUserName());
        newUser.setPassword(userRequestDTO.getPassword());
        newUser.setEmail(userRequestDTO.getEmail());
        newUser.setRole(Role.USER);
        return newUser;
    }

    @Override
    public  UserResponseDTO toDTO(User user) {
        UserResponseDTO newUserResponseDTO = new UserResponseDTO();
        newUserResponseDTO.setId(user.getId());
        newUserResponseDTO.setUserName(user.getUsername());
        newUserResponseDTO.setEmail(user.getEmail());
        return newUserResponseDTO;
    }
}
