package com.example.finalproject.service;

import com.example.finalproject.dto.TelegramResponseType;
import com.example.finalproject.repository.TelegramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ApiService {
    @Autowired
    TelegramRepository repository;

    private final JsoupService jsoupService;

    public TelegramResponseType getTranslationResult(String word, String from, String to) throws IOException {
        return jsoupService.jsoupService("az",word,from,to);


    }
}
