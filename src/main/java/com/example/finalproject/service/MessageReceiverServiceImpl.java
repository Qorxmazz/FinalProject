package com.example.finalproject.service;

import com.example.finalproject.dto.telegram.update.TelegramResponseDTO;
import com.example.finalproject.dto.telegram.update.TelegramUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MessageReceiverServiceImpl  {
    @Autowired
    RestService restService;
    @Value("base-url")
    private String telegramApiBaseUrl;
    @Value("api-token")
    private String botToken;
    @Value("bot-name")
    private String botName;

    private Long offset = null;

    public TelegramUpdateDTO updateAlanMethod() {
        String url = telegramApiBaseUrl + "/bot" + botToken + "/getUpdates";
        if (offset != null)
            url = url + "?offset=" + offset;
        RestTemplate restTemplate = new RestTemplate();
        TelegramResponseDTO telegramResponseDTO = restTemplate.getForObject(url, TelegramResponseDTO.class);
        if (telegramResponseDTO.getResult().size() > 0) {
            if (telegramResponseDTO.getResult().get(0).getMessageDTO() != null) {
                TelegramUpdateDTO telegramUpdateDTO = telegramResponseDTO.getResult().get(0);
                offset = telegramUpdateDTO.getUpdateId() + 1;
                return telegramUpdateDTO;
            } else {
                offset = telegramResponseDTO.getResult().get(0).getUpdateId() + 1;
                return null;
            }
        } else
            return null;

    }
}
