package com.project.chatop.service;

import com.project.chatop.port.service.PictureService;
import com.project.chatop.port.service.RentalService;
import com.project.chatop.port.service.UserService;
import com.project.chatop.mapper.RentalMapper;
import com.project.chatop.entity.Rental;
import com.project.chatop.port.repository.RentalRepository;
import com.project.chatop.dto.request.RentalRequest;
import com.project.chatop.exception.RentalNotFoundException;
import com.project.chatop.entity.User;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * RentalService qui permet la gestion des Rentals.
 */
@Log4j2
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

    /**
     * Récupère la liste des rentals existant en base de données.
     * @return List<Rental> la liste des rentals.
     */
    public List<Rental> getAll() {
        log.info("RentalService : getAll");
        return this.rentalRepository.findAll();
    }

    /**
     * Récupère un rental par son id.
     * @param id identifiant du rental
     * @return Rental
     */
    public Rental getById(Long id) {
        log.info("RentalService : getById {}", id);
        return this.rentalRepository.findRentalById(id).orElseThrow(RentalNotFoundException::new);
    }

    /**
     * Crée un nouveau rental.
     * On recupère l'utilisateur authentifié qui sera le propriétaire du rental.
     * On sauvegarde l'image au serveur dont on récupère l'url à stocker en base de données.
     * On mappe le RentalRequest pour créer un Rental à sauvegarder.
     * On sauvegarde le rental en base de données.
     * Exceptions : IOException pour l'image.
     * @param rentalRequest DTO
     * @param userId id de l'utilisateur authentifié.
     * @return Rental créé et sauvegardé en base de données.
     */
    @SneakyThrows(IOException.class)
    @Transactional(rollbackOn = {IOException.class})
    public Rental create(RentalRequest rentalRequest, Long userId) {
        log.info("RentalService : create {} | id : {}", rentalRequest, userId);
        User owner =  this.userService.getUser(userId);
        String url = pictureService.saveImage(rentalRequest.picture());
        Rental rentalSaved = rentalMapper.toCreateRental(rentalRequest, url, owner);
        return this.rentalRepository.save(rentalSaved);
    }

    /**
     * Update d'un rental.
     * On récupère le rental existant en base de données.
     * On mappe le RentalRequest pour mettre à jour le Rental existant.
     * On utilise le dirty checking d'hibertane pour faire l'update depuis le rentalMapper.
     * @param rentalRequest DTO
     * @param rentalId l'identifiant du rental à mettre à jour.
     * @return Rental mis à jour.
     */
    @Transactional
    public Rental update(RentalRequest rentalRequest, Long rentalId) {
        log.info("RentalService : update {} | id : {}", rentalRequest, rentalId);
        Rental rental = this.getById(rentalId);
        return this.rentalMapper.toUpdateRental(rental, rentalRequest);
    }
}
