package com.project.chatop.features.users.application.services;

import com.project.chatop.features.users.domain.entities.User;
import com.project.chatop.features.users.domain.repositories.UserRepository;
import com.project.chatop.features.users.web.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long userId) {
        User user = this.userRepository.findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }
}
