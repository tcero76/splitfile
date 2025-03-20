package com.example.splitfile.services;

import com.example.splitfile.payload.ReadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public interface IFileService {

    List<String> WriteFile(MultipartFile file, Integer size) throws IOException;

    ReadFile ReadFile(String filename) throws FileNotFoundException;
}
