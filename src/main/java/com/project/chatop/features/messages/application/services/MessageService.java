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
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final RentalService rentalService;
    private final UserService userService;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageService(
            RentalService rentalService,
            UserService userService,
            MessageRepository messageRepository,
            MessageMapper messageMapper
    ) {
        this.rentalService = rentalService;
        this.userService = userService;
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    public Message create(MessageRequest messageRequest) {
        try {
            Rental rental = rentalService.getById(messageRequest.rental_id());
            User user = userService.getUser(messageRequest.user_id());
            Message message = messageMapper.toCreateMessage(messageRequest, user, rental);
            return messageRepository.save(message);
        } catch (Exception e) {
            throw new MessageNotCreatedException();
        }
    }
}
