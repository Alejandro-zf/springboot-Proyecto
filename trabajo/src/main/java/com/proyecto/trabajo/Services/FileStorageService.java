package com.proyecto.trabajo.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir:uploads/espacios}")
    private String uploadDir;

    public String saveBase64Image(String base64Image) throws IOException {
        // Crear directorio si no existe
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Extraer el tipo de imagen y los datos
        String[] parts = base64Image.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Formato de imagen Base64 inválido");
        }

        // Obtener extensión del archivo desde el tipo MIME
        String mimeType = parts[0]; // data:image/png;base64
        String extension = getExtensionFromMimeType(mimeType);
        
        // Decodificar Base64
        byte[] imageBytes = Base64.getDecoder().decode(parts[1]);

        // Generar nombre único para el archivo
        String fileName = UUID.randomUUID().toString() + "." + extension;
        Path filePath = uploadPath.resolve(fileName);

        // Guardar archivo
        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            fos.write(imageBytes);
        }

        // Retornar la URL relativa
        return "/uploads/espacios/" + fileName;
    }

    private String getExtensionFromMimeType(String mimeType) {
        if (mimeType.contains("jpeg") || mimeType.contains("jpg")) {
            return "jpg";
        } else if (mimeType.contains("png")) {
            return "png";
        } else if (mimeType.contains("gif")) {
            return "gif";
        } else if (mimeType.contains("webp")) {
            return "webp";
        }
        return "jpg"; // Por defecto
    }

    public boolean deleteImage(String imageUrl) {
        try {
            if (imageUrl != null && imageUrl.startsWith("/uploads/espacios/")) {
                String fileName = imageUrl.substring("/uploads/espacios/".length());
                Path filePath = Paths.get(uploadDir, fileName);
                Files.deleteIfExists(filePath);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
