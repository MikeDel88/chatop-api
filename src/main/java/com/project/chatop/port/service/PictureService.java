package com.project.chatop.port.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

/**
 * PictureService qui permet le traitement de l'image et sa sauvegarde sur le serveur.
 */
public interface PictureService {
    /**
     * Enregistre l'image sur le serveur dans avec un path déterminé dans la configuration de l'application.
     * @param file MultipartFile, image à sauvegarder.
     * @return String url pour accéder à l'image depuis un navigateur.
     * @throws IOException en cas d'erreur sur l'image ou la sauvegarde.
     */
    String saveImage(MultipartFile file) throws IOException;
}

