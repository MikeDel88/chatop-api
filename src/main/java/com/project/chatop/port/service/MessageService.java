package com.project.chatop.port.service;

import com.project.chatop.entity.Message;
import com.project.chatop.dto.request.MessageRequest;


public interface MessageService {
    Message create(MessageRequest messageRequest, Long userId);
}

