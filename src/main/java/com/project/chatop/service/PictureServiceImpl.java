package com.project.chatop.service;

import com.project.chatop.port.service.PictureService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Log4j2
@Service
public class PictureServiceImpl implements PictureService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.api.base-url}")
    private String baseUrl;


    public String saveImage(MultipartFile file) throws IOException {

        log.debug("Picture Service : Upload image file {}", file.getOriginalFilename());
        log.debug("Picture Service : Upload image file type {}", file.getContentType());

        if(file.isEmpty()) {
            throw new IOException("Failed to upload file");
        }

        String contentType = file.getContentType();
        assert contentType != null;
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
            throw new IllegalArgumentException("Only JPEG or PNG images are allowed");
        }

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = file.getOriginalFilename();
        if(fileName == null) {
            throw new IOException("Failed to upload file");
        }

        int dotIndex = fileName.lastIndexOf('.');
        String extension = (dotIndex > 0) ? fileName.substring(dotIndex + 1) : "";

        UUID uuid = UUID.randomUUID();
        fileName = "rental_" + uuid + "." + extension;
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        String url = getUrl(fileName);
        log.debug("Picture Service : Image uploaded successfully with URL {}", url);
        return url;
    }

    private String getUrl(String fileName) {
        return baseUrl + "/images/" + fileName;
    }
}
