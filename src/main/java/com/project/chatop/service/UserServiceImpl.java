package com.project.chatop.service;

import com.project.chatop.entity.User;
import com.project.chatop.port.service.UserService;
import com.project.chatop.port.repository.UserRepository;
import com.project.chatop.exception.UserNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * UserService permet de récupérer les informations de profil d'un utilisateur.
 */
@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Récupère en base de données les informations d'un utilisateur.
     * @param userId identifiant de l'utilisateur à trouvé.
     * @return User.
     */
    public User getUser(Long userId) {
        log.info("UserService : getUser {}", userId);
        return this.userRepository.findUserById(userId).orElseThrow(UserNotFoundException::new);
    }
}
