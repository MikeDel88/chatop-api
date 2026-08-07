package com.project.chatop.mapper;

import com.project.chatop.dto.request.MessageRequest;
import com.project.chatop.entity.Message;
import com.project.chatop.entity.Rental;
import com.project.chatop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "rental", source = "rental")
    @Mapping(target = "message", source = "messageRequest.message")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Message toCreateMessage(MessageRequest messageRequest, Rental rental, User user);
}
