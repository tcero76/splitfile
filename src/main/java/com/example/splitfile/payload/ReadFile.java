package com.example.splitfile.payload;

import lombok.Data;
import org.springframework.core.io.InputStreamResource;

@Data
public class ReadFile {
    public ReadFile(InputStreamResource inputStreamResource, long length) {
        this.inputStreamResource = inputStreamResource;
        this.length = length;
    }
    InputStreamResource inputStreamResource;
    long length;
}
