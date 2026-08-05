package com.project.chatop.mapper;

import com.project.chatop.entity.Message;
import com.project.chatop.dto.request.MessageRequest;
import com.project.chatop.entity.Rental;
import com.project.chatop.entity.User;
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
