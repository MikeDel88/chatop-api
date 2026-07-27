package com.project.chatop.features.rentals.application.services;

import com.project.chatop.common.web.dtos.ConfirmResponse;
import com.project.chatop.features.rentals.application.mappers.RentalMapper;
import com.project.chatop.features.rentals.domain.entities.Rental;
import com.project.chatop.features.rentals.domain.repositories.RentalRepository;
import com.project.chatop.features.rentals.web.dtos.RentalRequest;
import com.project.chatop.features.rentals.web.dtos.RentalResponse;
import com.project.chatop.features.rentals.web.exceptions.PictureNotFoundException;
import com.project.chatop.features.rentals.web.exceptions.RentalNotCreatedException;
import com.project.chatop.features.rentals.web.exceptions.RentalNotFoundException;
import com.project.chatop.features.users.domain.entities.User;
import com.project.chatop.features.users.domain.repositories.UserRepository;
import com.project.chatop.features.users.web.exceptions.UserNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;

import java.net.MalformedURLException;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final RentalMapper rentalMapper;
    private final PictureService pictureService;

    public RentalService(RentalRepository rentalRepository, UserRepository userRepository, RentalMapper rentalMapper, PictureService pictureService) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.rentalMapper = rentalMapper;
        this.pictureService = pictureService;
    }

    public List<RentalResponse> getAll() {
        return this.rentalRepository
                .findAll()
                .stream()
                .map(rentalMapper::toRentalResponse)
                .collect(toList());
    }

    public RentalResponse getById(Long id) {
        Rental rental = this.rentalRepository.findRentalById(id);
        if(rental == null){
            throw new RentalNotFoundException();
        }
        return rentalMapper.toRentalResponse(rental);
    }

    public ConfirmResponse create(RentalRequest rentalRequest, Long userId) {
        try {
            User owner = this.userRepository.findUserById(userId);
            if(owner == null){
                throw new UserNotFoundException();
            }
            String fileName = pictureService.saveImage(rentalRequest.picture());
            Rental rental = rentalMapper.toRental(null, rentalRequest, fileName, owner);
            this.rentalRepository.save(rental);
            return new ConfirmResponse("Rental created !");
        } catch (Exception e) {
            throw new RentalNotCreatedException();
        }
    }

    public ConfirmResponse update(RentalRequest rentalRequest, Long userId, Long rentalId) {
        return new ConfirmResponse("Rental updated !");
    }

    public ResponseEntity<Resource> getPicture(String filename) {
        try {
            Resource resource = this.pictureService.getImage(filename);

            if (!resource.exists())
                throw new PictureNotFoundException();

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);

        } catch (MalformedURLException e) {
            throw new PictureNotFoundException();
        }
    }
}
