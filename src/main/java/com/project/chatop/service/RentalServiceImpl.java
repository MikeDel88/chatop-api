package com.project.chatop.service;

import com.project.chatop.port.service.PictureService;
import com.project.chatop.port.service.RentalService;
import com.project.chatop.port.service.UserService;
import com.project.chatop.mapper.RentalMapper;
import com.project.chatop.entity.Rental;
import com.project.chatop.port.repository.RentalRepository;
import com.project.chatop.dto.request.RentalRequest;
import com.project.chatop.exception.RentalNotCreatedException;
import com.project.chatop.exception.RentalNotFoundException;
import com.project.chatop.exception.RentalNotUpdatedException;
import com.project.chatop.entity.User;
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
