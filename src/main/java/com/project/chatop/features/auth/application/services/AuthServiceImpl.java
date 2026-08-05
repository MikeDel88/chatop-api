package com.project.chatop.features.auth.application.services;

import com.project.chatop.features.auth.application.utils.HashEncoder;
import com.project.chatop.features.auth.web.dtos.AuthResponse;
import com.project.chatop.features.auth.web.dtos.LoginRequest;
import com.project.chatop.features.auth.web.dtos.RegisterRequest;
import com.project.chatop.features.auth.web.exceptions.BadAuthenticationException;
import com.project.chatop.features.users.application.mappers.UserMapper;
import com.project.chatop.features.users.domain.entities.User;
import com.project.chatop.features.users.domain.repositories.UserRepository;
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
        User userSaved = this.userMapper.toUser(registerRequest, hashEncoder);
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
