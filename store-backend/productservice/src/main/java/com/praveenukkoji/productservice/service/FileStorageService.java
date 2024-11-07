package com.praveenukkoji.productservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path uploadDir = Paths.get("productservice/src/main/resources/uploads");

    // Helper method to extract file extension
    private String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            return fileName.substring(index); // Extracts file extension (e.g., .jpg, .png)
        }
        return ""; // Default to no extension if none is found
    }

    public String storeFile(MultipartFile file) throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir); // Create directory if it doesn’t exist
        }

        // file name
        String fileName = UUID.randomUUID() + getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));

        // Resolve the full path where the file will be saved
        Path filePath = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        // Return the URL path for accessing the file
        return "/uploads/" + fileName;
    }
}