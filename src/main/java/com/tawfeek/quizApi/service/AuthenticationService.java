package com.tawfeek.quizApi.service;

import com.tawfeek.quizApi.model.auth.AuthenticationRequest;
import com.tawfeek.quizApi.model.auth.AuthenticationResponse;
import com.tawfeek.quizApi.model.user.UserRequestDTO;

public interface AuthenticationService {

    public AuthenticationResponse register(UserRequestDTO request);

    public AuthenticationResponse authenticate(AuthenticationRequest request);


}
