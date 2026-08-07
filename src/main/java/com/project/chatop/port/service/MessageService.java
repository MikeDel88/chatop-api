package com.project.chatop.port.service;

import com.project.chatop.entity.Message;
import com.project.chatop.dto.request.MessageRequest;

/**
 * MessageService qui permet la création d'un message lié à un rental en base de données.
 */
public interface MessageService {
    /**
     * Création d'un message affilié à un rental et un user.
     * On récupère si le rental existe.
     * On récupère si le user de la session côté front existe.
     * @param messageRequest DTO qui contient user_id et rental_id.
     * @param userId l'id de l'utilisateur authentifié par l'application.
     * @return Message
     */
    Message create(MessageRequest messageRequest, Long userId);
}

