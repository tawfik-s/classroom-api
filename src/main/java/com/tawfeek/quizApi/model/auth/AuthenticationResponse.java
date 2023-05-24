package com.tawfeek.quizApi.model.auth;

import com.tawfeek.quizApi.model.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  private String token;

  private UserResponseDTO userResponseDTO;
}
