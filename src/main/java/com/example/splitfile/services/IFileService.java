package com.example.splitfile.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface IFileService {

    List<String> WriteFile(MultipartFile file, Integer size) throws IOException;

}
