package com.project.chatop.service;

import com.project.chatop.entity.User;
import com.project.chatop.port.service.UserService;
import com.project.chatop.port.repository.UserRepository;
import com.project.chatop.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long userId) {
        return this.userRepository.findUserById(userId).orElseThrow(UserNotFoundException::new);
    }
}
