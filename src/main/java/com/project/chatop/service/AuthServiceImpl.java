package com.project.chatop.service;

import com.project.chatop.application.HashEncoder;
import com.project.chatop.port.service.AuthService;
import com.project.chatop.port.service.JwtService;
import com.project.chatop.dto.response.AuthResponse;
import com.project.chatop.dto.request.LoginRequest;
import com.project.chatop.dto.request.RegisterRequest;
import com.project.chatop.exception.BadAuthenticationException;
import com.project.chatop.mapper.UserMapper;
import com.project.chatop.entity.User;
import com.project.chatop.port.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final HashEncoder hashEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(
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

    @Transactional(rollbackOn = BadAuthenticationException.class)
    public AuthResponse setRegister(RegisterRequest registerRequest) {
        User user = this.userMapper.toUser(registerRequest, hashEncoder);
        User userSaved = this.userRepository.save(user);
        return createResponse(userSaved);
    }

    public AuthResponse setLogin(LoginRequest loginRequest) {

        User user = this.userRepository.findUserByEmail(loginRequest.email()).orElse(null);

        this.isAuthenticated(loginRequest, user);

        assert user != null;
        return createResponse(user);
    }

    private AuthResponse createResponse(User user) {
        String token = jwtService.generateAccessToken(String.valueOf(user.getId()));
        return new AuthResponse(token);
    }

    private void isAuthenticated(LoginRequest request, User user) throws BadAuthenticationException {
        String fakeHash = "2a107s46EoKwqgSCgL58gT47VOEeeaTfkeWI9eVIdSxM91Ku9lCRmsWmG";
        String hashPassword = user == null ? fakeHash : user.getPassword();

        if(!hashEncoder.matches(request.password(), hashPassword))
            throw new BadAuthenticationException("Une erreur est survenue lors de la connexion");
    }

}
