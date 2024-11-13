package com.praveenukkoji.productservice.service;

import com.praveenukkoji.productservice.exception.product.ProductImageNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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

    private final Path imageDir = Paths.get("productservice/src/main/resources/uploads");

    // helper method to extract file extension
    private String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            return fileName.substring(index); // Extracts file extension (e.g., .jpg, .png)
        }
        return ""; // Default to no extension if none is found
    }

    // store file
    public String storeFile(MultipartFile file) throws IOException {
        if (!Files.exists(imageDir)) {
            Files.createDirectories(imageDir); // Create directory if it doesn’t exist
        }

        // file name
        String fileName = UUID.randomUUID() + getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));

        // Resolve the full path where the file will be saved
        Path filePath = imageDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        // Return the file name
        return fileName;
    }

    // delete file
    public void deleteFile(String imageName) throws IOException {
        Path filePath = Paths.get(String.valueOf(imageDir), imageName);
        Files.deleteIfExists(filePath); // Delete file if exists
    }

    // get file
    public Resource getImage(String imageId) throws ProductImageNotFoundException {
        try {
            // Try to find the image with multiple common extensions
            String[] extensions = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};
            Resource resource = null;

            for (String ext : extensions) {
                Path filePath = imageDir.resolve(imageId + ext).normalize();
                resource = new UrlResource(filePath.toUri());
                if (resource.exists() && resource.isReadable()) {
                    break;
                }
            }

            // If no valid resource is found
            if (!resource.exists() || !resource.isReadable()) {
                throw new ProductImageNotFoundException("image with id = " + imageId + " not found");
            }

            return resource;

        } catch (Exception e) {
            throw new ProductImageNotFoundException(e.getMessage());
        }
    }
}