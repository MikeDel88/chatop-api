package com.project.chatop.features.auth.application.services;

import com.project.chatop.features.auth.application.mappers.UserMapper;
import com.project.chatop.features.auth.application.utils.HashEncoder;
import com.project.chatop.features.auth.domain.entities.User;
import com.project.chatop.features.auth.domain.repositories.UserRepository;
import com.project.chatop.features.auth.web.dtos.AuthResponse;
import com.project.chatop.features.auth.web.dtos.LoginRequest;
import com.project.chatop.features.auth.web.dtos.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final HashEncoder hashEncoder;

    public AuthService(
            UserRepository repo,
            UserMapper userMapper,
            HashEncoder hashEncoder
    ) {
        this.userRepository = repo;
        this.userMapper = userMapper;
        this.hashEncoder = hashEncoder;
    }

    public AuthResponse setRegister(RegisterRequest registerRequest) {
        //TODO: Ajouter la gestion du token JWT
        User user = this.userMapper.toUser(registerRequest, hashEncoder);
        this.userRepository.save(user);
        return new AuthResponse("jwt");
    }

    public AuthResponse setLogin(LoginRequest loginRequest) throws Exception {
        //TODO: Ajouter la gestion du token JWT
        String email = loginRequest.email();
        String password = loginRequest.password();
        User user = this.userRepository.findUserByEmail(email);

        String fakeHash = "2a107s46EoKwqgSCgL58gT47VOEeeaTfkeWI9eVIdSxM91Ku9lCRmsWmG";
        String hashPassword = user == null ? fakeHash : user.password();

        Boolean isAuthenticated = hashEncoder.matches(password, hashPassword);

        if(!isAuthenticated) {
            throw new Exception("Invalid Login Credentials");
        }

        return new AuthResponse("jwt");
    }

}
