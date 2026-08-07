package com.project.chatop.port.service;

import com.project.chatop.entity.Rental;
import com.project.chatop.dto.request.RentalRequest;

import java.util.List;

/**
 * RentalService qui permet la gestion des Rentals.
 */
public interface RentalService {
    /**
     * Récupère la liste des rentals existant en base de données.
     * @return List<Rental> la liste des rentals.
     */
    List<Rental> getAll();
    /**
     * Récupère un rental par son id.
     * @param id identifiant du rental
     * @return Rental
     */
    Rental getById(Long id);
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
    Rental create(RentalRequest rentalRequest, Long userId);
    /**
     * Update d'un rental.
     * On récupère le rental existant en base de données.
     * On mappe le RentalRequest pour mettre à jour le Rental existant.
     * On utilise le dirty checking d'hibertane pour faire l'update depuis le rentalMapper.
     * @param rentalRequest DTO
     * @param rentalId l'identifiant du rental à mettre à jour.
     * @return Rental mis à jour.
     */
    Rental update(RentalRequest rentalRequest, Long rentalId);
}

