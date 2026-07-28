package com.project.chatop.features.rentals.application.services;

import com.project.chatop.features.rentals.application.mappers.RentalMapper;
import com.project.chatop.features.rentals.domain.entities.Rental;
import com.project.chatop.features.rentals.domain.repositories.RentalRepository;
import com.project.chatop.features.rentals.web.dtos.RentalRequest;
import com.project.chatop.features.rentals.web.exceptions.RentalNotCreatedException;
import com.project.chatop.features.rentals.web.exceptions.RentalNotFoundException;
import com.project.chatop.features.rentals.web.exceptions.RentalNotUpdatedException;
import com.project.chatop.features.users.application.services.UserService;
import com.project.chatop.features.users.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final UserService userService;
    private final PictureService pictureService;

    public RentalService(
            RentalRepository rentalRepository,
            RentalMapper rentalMapper,
            UserService userService,
            PictureService pictureService
    ) {
        this.rentalRepository = rentalRepository;
        this.rentalMapper = rentalMapper;
        this.userService = userService;
        this.pictureService = pictureService;
    }

    public List<Rental> getAll() {
        return this.rentalRepository.findAll();
    }

    public Rental getById(Long id) {
        Rental rental = this.rentalRepository.findRentalById(id);
        if(rental == null){
            throw new RentalNotFoundException();
        }
        return rental;
    }

    public Rental create(RentalRequest rentalRequest, Long userId) {
        try {
            User owner =  this.userService.getUser(userId);
            String url = pictureService.saveImage(rentalRequest.picture());
            Rental rental = rentalMapper.toCreateRental(rentalRequest, url, owner);
            return this.rentalRepository.save(rental);
        } catch (Exception e) {
            throw new RentalNotCreatedException();
        }
    }

    public Rental update(RentalRequest rentalRequest, Long rentalId) {
        try {
            Rental rental = this.getById(rentalId);
            Rental rentalToUpdate = this.rentalMapper.toUpdateRental(rental, rentalRequest);
            return this.rentalRepository.save(rentalToUpdate);
        } catch (Exception e) {
            throw new RentalNotUpdatedException();
        }
    }
}
