package com.project.chatop.features.rentals.application.services;

import com.project.chatop.common.web.dtos.ConfirmResponse;
import com.project.chatop.features.rentals.application.mappers.RentalMapper;
import com.project.chatop.features.rentals.domain.entities.Rental;
import com.project.chatop.features.rentals.domain.repositories.RentalRepository;
import com.project.chatop.features.rentals.web.dtos.RentalRequest;
import com.project.chatop.features.rentals.web.dtos.RentalResponse;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<RentalResponse> getAll() {
        RentalMapper rentalMapper = new RentalMapper();
        return this.rentalRepository
                .findAll()
                .stream()
                .map(rentalMapper::toRentalResponse)
                .collect(toList());
    }

    public RentalResponse getById(Long id) {
        RentalMapper rentalMapper = new RentalMapper();
        Rental rental = this.rentalRepository.findRentalById(id);
        if(rental == null){
            //TODO: Renvoyer une exception.
            return null;
        }
        return rentalMapper.toRentalResponse(rental);
    }

    public ConfirmResponse create(RentalRequest rentalRequest, Long userId) {
        return new ConfirmResponse("created ok");
    }

    public ConfirmResponse update(RentalRequest rentalRequest, Long userId) {
        return new ConfirmResponse("updated ok");
    }


}
