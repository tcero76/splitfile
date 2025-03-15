package com.example.splitfile.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo está vacío");
        }

        try {
            // Ruta donde se guardará el archivo (ajustar según sea necesario)
            String uploadDir = "uploads/";
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs(); // Crea la carpeta si no existe
            }

            // Guarda el archivo en la carpeta especificada
            File destFile = new File(uploadDir + file.getOriginalFilename());
            file.transferTo(destFile);

            return ResponseEntity.ok("Archivo subido exitosamente: " + file.getOriginalFilename());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el archivo: " + e.getMessage());
        }
    }
}

