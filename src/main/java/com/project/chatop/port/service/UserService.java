package com.project.chatop.port.service;

import com.project.chatop.entity.User;

/**
 * UserService permet de récupérer les informations de profil d'un utilisateur.
 */
public interface UserService {

    /**
     * Récupère en base de données les informations d'un utilisateur.
     * @param userId identifiant de l'utilisateur à trouvé.
     * @return User.
     */
    User getUser(Long userId);
}

