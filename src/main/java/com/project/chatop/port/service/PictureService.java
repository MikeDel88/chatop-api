package com.project.chatop.port.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface PictureService {
    String saveImage(MultipartFile file) throws IOException;
}

