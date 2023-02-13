package com.example.finalproject.controller;

import com.example.finalproject.dto.telegram.update.TelegramResponseDTO;
import com.example.finalproject.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyController {
    private final DatabaseService service;

    @PostMapping("myTelegram")
    public void getMessage(@RequestBody TelegramResponseDTO dto) {
        service.saveToDb(dto);
    }
}

