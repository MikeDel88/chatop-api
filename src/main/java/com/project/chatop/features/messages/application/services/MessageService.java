package com.project.chatop.features.messages.application.services;

import com.project.chatop.features.messages.domain.entities.Message;
import com.project.chatop.features.messages.web.dtos.MessageRequest;


public interface MessageService {
    Message create(MessageRequest messageRequest);
}

