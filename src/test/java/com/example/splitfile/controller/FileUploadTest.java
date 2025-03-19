package com.example.splitfile.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FileUploadTest {
    @Autowired
    private MockMvc mockMvc;

    static Stream<Object[]> parametros() {
        return Stream.of(
                new Object[]{"Contenido de prueba", 4},
                new Object[]{"Contenido de prueba1", 10},
                new Object[]{"Contenido de prueba2", 5}
        );
    }
    @ParameterizedTest
    @MethodSource("parametros")
    public void testFileUpload(String body, Integer size) throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", "multipart/form-data", body.getBytes()
        );
        mockMvc.perform(multipart("/api/upload")
                        .file(file)
                        .param("size",size.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("Archivo subido exitosamente: test.txt")));
    }
}
