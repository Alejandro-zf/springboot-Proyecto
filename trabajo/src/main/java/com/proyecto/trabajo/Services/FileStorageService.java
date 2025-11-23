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

    @Value("${file.upload-dir:uploads}")
    private String baseUploadDir;

    // Método para guardar imágenes (por defecto en espacios para compatibilidad)
    public String saveBase64Image(String base64Image) throws IOException {
        return saveBase64Image(base64Image, "espacios");
    }

    // Método sobrecargado para especificar la carpeta
    public String saveBase64Image(String base64Image, String folder) throws IOException {
        // Crear directorio si no existe
        Path uploadPath = Paths.get(baseUploadDir, folder);
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
        return "/uploads/" + folder + "/" + fileName;
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
            if (imageUrl != null && imageUrl.startsWith("/uploads/")) {
                // Extraer la ruta relativa después de /uploads/
                String relativePath = imageUrl.substring("/uploads/".length());
                Path filePath = Paths.get(baseUploadDir, relativePath);
                Files.deleteIfExists(filePath);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
