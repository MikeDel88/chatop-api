package com.project.chatop.features.messages.application.mappers;

import com.project.chatop.features.messages.domain.entities.Message;
import com.project.chatop.features.messages.web.dtos.MessageRequest;
import com.project.chatop.features.rentals.domain.entities.Rental;
import com.project.chatop.features.users.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public Message toCreateMessage(MessageRequest messageRequest, User user, Rental rental) {
        if(messageRequest == null) {
            return null;
        }
        Message message = new Message();
        message.setUser(user);
        message.setRental(rental);
        message.setMessage(messageRequest.message());
        return message;
    }
}
