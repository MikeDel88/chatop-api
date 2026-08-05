package com.project.chatop.service;

import com.project.chatop.port.service.MessageService;
import com.project.chatop.port.service.RentalService;
import com.project.chatop.port.service.UserService;
import com.project.chatop.mapper.MessageMapper;
import com.project.chatop.entity.Message;
import com.project.chatop.port.repository.MessageRepository;
import com.project.chatop.dto.request.MessageRequest;
import com.project.chatop.exception.MessageNotCreatedException;
import com.project.chatop.entity.Rental;
import com.project.chatop.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final RentalService rentalService;
    private final UserService userService;
    private final MessageMapper messageMapper;
    private final MessageRepository messageRepository;

    public MessageServiceImpl(
            RentalService rentalService,
            UserService userService,
            MessageMapper messageMapper, MessageRepository messageRepository
    ) {
        this.rentalService = rentalService;
        this.userService = userService;
        this.messageMapper = messageMapper;
        this.messageRepository = messageRepository;
    }

    @Transactional(rollbackOn = MessageNotCreatedException.class)
    public Message create(MessageRequest messageRequest) {
        Rental rental = rentalService.getById(messageRequest.rental_id());
        User user = userService.getUser(messageRequest.user_id());
        Message message = messageMapper.toCreateMessage(messageRequest, user, rental);
        return this.messageRepository.save(message);
    }
}
