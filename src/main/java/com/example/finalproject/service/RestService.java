package com.example.finalproject.service;

import com.example.finalproject.dto.telegram.update.TelegramResponseDTO;
import com.example.finalproject.dto.telegram.update.TelegramUpdateDTO;
import com.example.finalproject.jsoup.JsoupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class RestService {


    @Value("${base-url}")
    String baseUrl;
    @Value("${api-token}")
    String token;
    @Autowired
    JsoupService jsoupService;
    private Long offset = null;

    public void getUpdates() throws IOException {

        String url = baseUrl + "/bot" + token + "/getUpdates";

        RestTemplate restTemplate = new RestTemplate();

        if (offset != null)
            url = url + "?offset=" + offset;
        TelegramResponseDTO telegramResponseDTO = restTemplate.getForObject(url, TelegramResponseDTO.class);
        int size = telegramResponseDTO.getResult().size();
        if (size > 0) {
            TelegramUpdateDTO telegramUpdateDTO = telegramResponseDTO.getResult().get(0);
            if (telegramUpdateDTO.getMessageDTO() != null) {
                String text = telegramUpdateDTO.getMessageDTO().getText();
                offset = telegramUpdateDTO.getUpdateId() + 1;
                jsoupService.jsoupService(text);
                System.out.println(text);
            }
        }
    }
}

