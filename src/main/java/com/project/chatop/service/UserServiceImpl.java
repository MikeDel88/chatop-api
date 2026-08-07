package com.project.chatop.service;

import com.project.chatop.entity.User;
import com.project.chatop.port.service.UserService;
import com.project.chatop.port.repository.UserRepository;
import com.project.chatop.exception.UserNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        log.info("UserService : getUser {}", userId);
        return this.userRepository.findUserById(userId).orElseThrow(UserNotFoundException::new);
    }
}
