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
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final UserService userService;
    private final PictureService pictureService;

    public RentalServiceImpl(
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
        return this.rentalRepository.findRentalById(id).orElseThrow(RentalNotFoundException::new);
    }

    @SneakyThrows(IOException.class)
    @Transactional(rollbackOn = {RentalNotCreatedException.class, IOException.class})
    public Rental create(RentalRequest rentalRequest, Long userId) {
        User owner =  this.userService.getUser(userId);
        String url = pictureService.saveImage(rentalRequest.picture());
        Rental rentalSaved = rentalMapper.toCreateRental(rentalRequest, url, owner);
        return this.rentalRepository.save(rentalSaved);
    }

    @Transactional(rollbackOn = RentalNotUpdatedException.class)
    public Rental update(RentalRequest rentalRequest, Long rentalId) {
        Rental rental = this.getById(rentalId);
        return this.rentalMapper.toUpdateRental(rental, rentalRequest);
    }
}
