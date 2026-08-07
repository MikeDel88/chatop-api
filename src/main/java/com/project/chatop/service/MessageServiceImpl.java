package com.project.chatop.service;

import com.project.chatop.port.service.MessageService;
import com.project.chatop.port.service.RentalService;
import com.project.chatop.mapper.MessageMapper;
import com.project.chatop.entity.Message;
import com.project.chatop.port.repository.MessageRepository;
import com.project.chatop.dto.request.MessageRequest;
import com.project.chatop.exception.MessageNotCreatedException;
import com.project.chatop.entity.Rental;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Log4j2
@Service
public class MessageServiceImpl implements MessageService {

    private final RentalService rentalService;
    private final MessageMapper messageMapper;
    private final MessageRepository messageRepository;

    public MessageServiceImpl(
            RentalService rentalService,
            MessageMapper messageMapper,
            MessageRepository messageRepository
    ) {
        this.rentalService = rentalService;
        this.messageMapper = messageMapper;
        this.messageRepository = messageRepository;
    }

    @Transactional
    public Message create(MessageRequest messageRequest, Long userId) {
        log.info("MessageService : create");

        Rental rental = rentalService.getById(messageRequest.rental_id());
        if(!Objects.equals(userId, messageRequest.user_id())) {
            throw new MessageNotCreatedException();
        }
        Message message = messageMapper.toCreateMessage(messageRequest, rental);
        return this.messageRepository.save(message);
    }
}
