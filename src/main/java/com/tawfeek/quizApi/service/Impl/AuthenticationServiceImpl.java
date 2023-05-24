package com.tawfeek.quizApi.service.Impl;


import com.tawfeek.quizApi.mapper.UserMapper;
import com.tawfeek.quizApi.model.auth.AuthenticationRequest;
import com.tawfeek.quizApi.model.auth.AuthenticationResponse;
import com.tawfeek.quizApi.model.user.UserRequestDTO;
import com.tawfeek.quizApi.model.user.UserResponseDTO;
import com.tawfeek.quizApi.repository.UserRepository;
import com.tawfeek.quizApi.security.JwtService;
import com.tawfeek.quizApi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;


    public AuthenticationResponse register(UserRequestDTO request) {
        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userResponseDTO(userMapper.toDTO(user))
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userResponseDTO(userMapper.toDTO(user))
                .build();
    }
}
