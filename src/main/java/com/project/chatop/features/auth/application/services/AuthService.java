package com.project.chatop.features.auth.application.services;

import com.project.chatop.features.auth.web.exceptions.BadAuthenticationException;
import com.project.chatop.features.users.application.mappers.UserMapper;
import com.project.chatop.features.auth.application.utils.HashEncoder;
import com.project.chatop.features.users.domain.entities.User;
import com.project.chatop.features.users.domain.repositories.UserRepository;
import com.project.chatop.features.auth.web.dtos.AuthResponse;
import com.project.chatop.features.auth.web.dtos.LoginRequest;
import com.project.chatop.features.auth.web.dtos.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final HashEncoder hashEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository repo,
            UserMapper userMapper,
            HashEncoder hashEncoder,
            JwtService jwtService
    ) {
        this.userRepository = repo;
        this.userMapper = userMapper;
        this.hashEncoder = hashEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse setRegister(RegisterRequest registerRequest) {
        User user = this.userMapper.toUser(registerRequest, hashEncoder);
        try {
            User userSaved = this.userRepository.save(user);
            return createResponse(userSaved);
        } catch (Exception e) {
            throw new BadAuthenticationException("Une erreur est survenue lors de l'enregistrement");
        }

    }

    public AuthResponse setLogin(LoginRequest loginRequest) {

        User user = this.userRepository.findUserByEmail(loginRequest.email());

        if(!this.isAuthenticated(loginRequest, user)) {
            throw new BadAuthenticationException("Une erreur est survenue lors de la connexion");
        }

        assert user != null;
        return createResponse(user);
    }

    private AuthResponse createResponse(User user) {
        String token = jwtService.generateAccessToken(String.valueOf(user.getId()));
        return new AuthResponse(token);
    }

    private Boolean isAuthenticated(LoginRequest request, User user) {
        String fakeHash = "2a107s46EoKwqgSCgL58gT47VOEeeaTfkeWI9eVIdSxM91Ku9lCRmsWmG";
        String hashPassword = user == null ? fakeHash : user.getPassword();

        return hashEncoder.matches(request.password(), hashPassword);
    }

}
