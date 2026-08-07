package com.project.chatop.mapper;

import com.project.chatop.dto.request.RegisterRequest;
import com.project.chatop.dto.response.UserResponse;
import com.project.chatop.entity.User;
import com.project.chatop.security.HashEncoder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", expression = "java(hashEncoder.encode(registerRequest.password()))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toUser(RegisterRequest registerRequest, @Context HashEncoder hashEncoder);

    @Mapping(target = "created_at", source = "createdAt", dateFormat = "yyyy/MM/dd")
    @Mapping(target = "updated_at", source = "updatedAt", dateFormat = "yyyy/MM/dd")
    UserResponse toUserResponse(User user);
}
