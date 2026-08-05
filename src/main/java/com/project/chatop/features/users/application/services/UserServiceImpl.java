package com.project.chatop.features.users.application.services;

import com.project.chatop.features.users.domain.entities.User;
import com.project.chatop.features.users.domain.repositories.UserRepository;
import com.project.chatop.features.users.web.exceptions.UserNotFoundException;
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
