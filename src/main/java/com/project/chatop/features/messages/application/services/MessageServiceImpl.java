package com.project.chatop.features.messages.application.services;

import com.project.chatop.features.messages.application.mappers.MessageMapper;
import com.project.chatop.features.messages.domain.entities.Message;
import com.project.chatop.features.messages.domain.repositories.MessageRepository;
import com.project.chatop.features.messages.web.dtos.MessageRequest;
import com.project.chatop.features.messages.web.exceptions.MessageNotCreatedException;
import com.project.chatop.features.rentals.application.services.RentalService;
import com.project.chatop.features.rentals.domain.entities.Rental;
import com.project.chatop.features.users.application.services.UserService;
import com.project.chatop.features.users.domain.entities.User;
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
