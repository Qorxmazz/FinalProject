package com.example.finalproject.service;

import com.example.finalproject.dto.TelegramResponseType;
import com.example.finalproject.dto.telegram.send.SendMessageResponseDTO;
import com.example.finalproject.dto.telegram.send.text.SendMessageDTO;
import com.example.finalproject.dto.telegram.update.TelegramResponseDTO;
import com.example.finalproject.dto.telegram.update.TelegramUpdateDTO;
import com.example.finalproject.enums.ChatStage;
import com.example.finalproject.entity.TelegramEntity;
import com.example.finalproject.repository.TelegramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RestService {


    private final DatabaseService databaseService;

    @Value("${base-url}")
    String baseUrl;
    @Value("${api-token}")
    String token;
    @Autowired
    JsoupService jsoupService;

    @Autowired
    TelegramRepository repository;

    private Long offset = null;


    public Object getUpdates() throws IOException {


        String url = baseUrl + "/bot" + token + "/getUpdates";


        if (offset != null)
            url = url + "?offset=" + offset;
        RestTemplate restTemplate = new RestTemplate();
        TelegramResponseDTO telegramResponseDTO = restTemplate.getForObject(url, TelegramResponseDTO.class);
        int size = telegramResponseDTO.getResult().size();
        if (size > 0) {
            TelegramUpdateDTO telegramUpdateDTO = telegramResponseDTO.getResult().get(0);

            if (telegramUpdateDTO.getMessageDTO() != null) {
                offset = telegramUpdateDTO.getUpdateId() + 1;
                String text = telegramUpdateDTO.getMessageDTO().getText();
                Long id = telegramUpdateDTO.getMessageDTO().getChat().getId();

                databaseService.saveToDb(telegramResponseDTO);

                TelegramEntity byChatId1 = repository.findByChatId(id);

                boolean extracted = extracted(id, text, byChatId1);

                if (byChatId1 != null && extracted) {
                    String chatStage = byChatId1.getChatStage();
                    if (chatStage.equals(ChatStage.FROM_LANG.name())) {
                        byChatId1.setFromLang(text);
                        String toLang = byChatId1.getToLang();
                        if (toLang == null) {
                            byChatId1.setChatStage(ChatStage.TO_LANG.name());
                            repository.save(byChatId1);
                            sendMessage("Zəhmət olmasa hansı dilə tərcümə etmək istədiyinizi seçin", id);
                        } else {
                            byChatId1.setChatStage(ChatStage.COMPLETED.name());
                            repository.save(byChatId1);
                            sendMessage("Dil seçiminiz uğurla yazıldl", id);
                        }

                    } else if (chatStage.equals(ChatStage.TO_LANG.name())) {
                        byChatId1.setToLang(text);
                        byChatId1.setChatStage(ChatStage.COMPLETED.name());
                        repository.save(byChatId1);
                        sendMessage("Seçimləriniz bazaya uğurla yazıldı", id);
                    } else if (chatStage.equals(ChatStage.COMPLETED.name())) {
                        String languageCode = telegramUpdateDTO.getMessageDTO().getFrom().getLanguageCode();
                        TelegramResponseType telegramResponseType = jsoupService.jsoupService(languageCode, text, byChatId1.getFromLang(), byChatId1.getToLang());
                        sendMessage(telegramResponseType.toString(), id);
                    }
                }
            }
        }
        return null;
    }

    private boolean extracted(Long id, String text, TelegramEntity byChatId1) throws IOException {
        switch (text) {
            case "/start" -> {
                TelegramEntity entity = TelegramEntity.builder().chatId(id).chatStage(ChatStage.FROM_LANG.name()).build();
                TelegramEntity byChatId = repository.findByChatId(id);
                if (byChatId == null) {
                    repository.save(entity);

                } else repository.save(byChatId);
                sendMessage("Salam Translate bota xoş gəlmisiniz!", id);
                sendMessage("Zəhmət olmasa hansı dildən tərcümə etmək istədiyinizi seçin", id);
                return false;
            }
            case "/fromlang" -> {
                TelegramEntity entity = TelegramEntity.builder().chatId(id).chatStage(ChatStage.FROM_LANG.name()).build();
                TelegramEntity byChatId = repository.findByChatId(id);
                if (byChatId == null) {
                    repository.save(entity);
                } else {
                    byChatId.setChatStage(ChatStage.FROM_LANG.name());
                    repository.save(byChatId);
                }
                sendMessage("Zəhmət olmasa hansı dildən tərcümə etmək istədiyinizi seçin", id);
                return false;
            }
            case "/tolang" -> {
                TelegramEntity entity = TelegramEntity.builder().chatId(id).chatStage(ChatStage.TO_LANG.name()).build();
                TelegramEntity byChatId = repository.findByChatId(id);
                if (byChatId == null) {
                    repository.save(entity);

                } else {
                    byChatId.setChatStage(ChatStage.TO_LANG.name());
                    repository.save(byChatId);
                }
                sendMessage("Zəhmət olmasa hansı dilə tərcümə etmək istədiyinizi seçin", id);
                return false;
            }
        }
        return true;
    }

    public SendMessageResponseDTO sendMessage(String text, Long id) throws IOException {
        String url = baseUrl + "/bot" + token + "/sendMessage";

        SendMessageDTO dto = SendMessageDTO.builder()
                .chatId(id)
                .text(text)
                .build();

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, dto, SendMessageResponseDTO.class);


    }
}

