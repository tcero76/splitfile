package com.example.splitfile.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileService implements IFileService{

    final String UPLOADDIR = "uploads/";

    @Override
    public List<String> WriteFile(MultipartFile file, Integer partSize) throws IOException {
        Path directorio = getDirectory();
        List<String> files = splitFile(file, partSize, directorio);
        saveFile(file, directorio);
        return files;
    }
    private Path getDirectory() throws IOException {
        Path directorio = Path.of(System.getProperty("java.io.tmpdir"), UPLOADDIR);
        if (!Files.isDirectory(directorio)) {
            Files.createDirectory(directorio);
        }
        return directorio;
    }
    private List<String> splitFile(MultipartFile file, Integer partSize, Path directorio) throws IOException {
        List<String> files = new ArrayList<>();
        long fileSize = file.getBytes().length;
        long NParts = fileSize / partSize.longValue();
        long remainingBytes = fileSize % (NParts * partSize);
        NParts = remainingBytes > 0 ? NParts + 1 : NParts;
        try (BufferedInputStream bis = new BufferedInputStream(file.getInputStream())) {
            for (int i = 0; i < NParts; i++) {
                Path partName = getPartName(directorio, i, file.getOriginalFilename());
                files.add(partName.toString());
                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(partName.toFile()))) {
                    long bytesToWrite = partSize + (i == NParts ? remainingBytes : 0);
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while (bytesToWrite > 0 && (bytesRead = bis.read(buffer, 0, (int) Math.min(buffer.length, bytesToWrite))) != -1) {
                        bos.write(buffer, 0, bytesRead);
                        bytesToWrite -= bytesRead;
                    }
                }
            }
        }
        return files;
    }

    private static Path getPartName(Path directorio, int i, String name) throws IOException {
        String[] strings = name.split("[.]", 2);
        if (strings.length == 2) {
            return directorio.resolve(strings[0] + i + "." + strings[1]);
        }
        return directorio.resolve(name + i);
    }

    private void saveFile(MultipartFile file, Path directorio) throws IOException {
        Path resolved = directorio.resolve(file.getOriginalFilename());
        Files.write(resolved, file.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
