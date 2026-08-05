package com.project.chatop.features.rentals.application.services;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface PictureService {
    String saveImage(MultipartFile file) throws IOException;
}

