package com.project.chatop.mapper;

import com.project.chatop.entity.Message;
import com.project.chatop.dto.request.MessageRequest;
import com.project.chatop.entity.Rental;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public Message toCreateMessage(MessageRequest messageRequest, Rental rental) {
        if(messageRequest == null) {
            return null;
        }
        Message message = new Message();
        message.setUser(rental.getOwner());
        message.setRental(rental);
        message.setMessage(messageRequest.message());
        return message;
    }
}
