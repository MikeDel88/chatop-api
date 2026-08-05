package com.project.chatop.features.users.application.services;

import com.project.chatop.features.users.domain.entities.User;

public interface UserService {
    User getUser(Long userId);
}

