package com.project.chatop.mapper;

import com.project.chatop.dto.request.RentalRequest;
import com.project.chatop.dto.response.RentalResponse;
import com.project.chatop.entity.Rental;
import com.project.chatop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    @Mapping(target = "name", source = "rentalRequest.name")
    @Mapping(target = "picture", source = "pictureUrl")
    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Rental toCreateRental(RentalRequest rentalRequest, String pictureUrl, User owner);

    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Rental toUpdateRental(@MappingTarget Rental rental, RentalRequest rentalRequest);

    @Mapping(target = "owner_id", source = "owner.id")
    @Mapping(target = "created_at", source = "createdAt", dateFormat = "yyyy/MM/dd")
    @Mapping(target = "updated_at", source = "updatedAt", dateFormat = "yyyy/MM/dd")
    RentalResponse toRentalResponse(Rental rental);
}
