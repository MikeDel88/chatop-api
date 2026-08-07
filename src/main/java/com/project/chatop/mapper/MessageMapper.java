package com.project.chatop.mapper;

import com.project.chatop.entity.Message;
import com.project.chatop.dto.request.MessageRequest;
import com.project.chatop.entity.Rental;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MessageMapper {

    public Message toCreateMessage(MessageRequest messageRequest, Rental rental) {
        log.info("toCreateMessage : {}", messageRequest);
        log.debug("toCreateMessage : {}", rental);

        Message message = new Message();
        message.setUser(rental.getOwner());
        message.setRental(rental);
        message.setMessage(messageRequest.message());

        log.debug("toCreateMessage : {}", message);

        return message;
    }
}
