package com.project.chatop.service;

import com.project.chatop.security.HashEncoder;
import com.project.chatop.port.service.AuthService;
import com.project.chatop.port.service.JwtService;
import com.project.chatop.dto.response.AuthResponse;
import com.project.chatop.dto.request.LoginRequest;
import com.project.chatop.dto.request.RegisterRequest;
import com.project.chatop.exception.BadAuthenticationException;
import com.project.chatop.mapper.UserMapper;
import com.project.chatop.entity.User;
import com.project.chatop.port.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
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

    @Transactional(rollbackFor = BadAuthenticationException.class)
    public AuthResponse setRegister(RegisterRequest registerRequest) {
        log.info("AuthService : Enregistrement de l'utilisateur");
        log.debug("register datas {}", registerRequest);
        User user = this.userMapper.toUser(registerRequest, hashEncoder);
        User userSaved = this.userRepository.save(user);
        return createResponse(userSaved);
    }

    @Transactional(readOnly = true)
    public AuthResponse setLogin(LoginRequest loginRequest) {
        log.info("AuthService : Connexion de l'utilisateur");
        log.debug("login datas {}", loginRequest);

        User user = this.userRepository.findUserByEmail(loginRequest.email()).orElse(null);

        this.isAuthenticated(loginRequest, user);

        assert user != null;
        return createResponse(user);
    }

    private AuthResponse createResponse(User user) {
        log.info("AuthService : Creation du token");

        String token = jwtService.generateAccessToken(String.valueOf(user.getId()));

        log.debug("AuthService : Token de l'utilisateur {}", token);

        return new AuthResponse(token);
    }

    private void isAuthenticated(LoginRequest request, User user) throws BadAuthenticationException {
        log.info("AuthService : Vérification de l'authentification");
        log.debug("isAuthenticated user {}", user);
        log.debug("isAuthenticated password {}", request.password());

        String fakeHash = "2a107s46EoKwqgSCgL58gT47VOEeeaTfkeWI9eVIdSxM91Ku9lCRmsWmG";
        String hashPassword = user == null ? fakeHash : user.getPassword();

        if(!hashEncoder.matches(request.password(), hashPassword))
            throw new BadAuthenticationException("Une erreur est survenue lors de la connexion");
    }

}
