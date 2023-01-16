package com.example.finalproject.service;

import com.example.finalproject.dto.telegram.send.SendMessageResponseDTO;
import com.example.finalproject.dto.telegram.send.text.SendMessageDTO;
import com.example.finalproject.dto.telegram.update.TelegramResponseDTO;
import com.example.finalproject.dto.telegram.update.TelegramUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageReceiverServiceImpl implements MessageReceiverService {
    private final HttpRequestService httpRequestService;

    @Autowired
    RestService restService;
    @Value("https:api.telegram.org")
    private String telegramApiBaseUrl;
    @Value("5945207239:AAHwOHQ4w2iFK4Ho0T4GpS821JgzSDDHW_Q")
    private String botToken;
    @Value("leqolasbot")
    private String botName;

    private Long offset = null;
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    @Override
    public TelegramUpdateDTO getUpdates() {
        String url = telegramApiBaseUrl + "/bot" + botToken + "/getUpdates";
        if (offset != null)
            url = url + "?offset=" + offset;
        TelegramResponseDTO telegramResponseDTO = httpRequestService.sendGetRequest(url, TelegramResponseDTO.class);
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

    @Override
    public SendMessageResponseDTO sendMessage(SendMessageDTO sendMessageDTO) {
        String url = telegramApiBaseUrl + "/bot" + botToken + "/sendMessage";
        SendMessageResponseDTO sendMessageResponseDTO = httpRequestService.sendPostRequest(url, sendMessageDTO, SendMessageResponseDTO);
        return sendMessageResponseDTO;

    }

    public SendMessageResponseDTO reply(TelegramUpdateDTO telegramUpdateDTO) throws IOException, ParseException {
        if (telegramUpdateDTO.getMessageDTO().getChat().getType().equals("group")) {
            String callName = "@" + botName;
            if (telegramUpdateDTO.getMessageDTO().getText().startsWith(callName)) {
                telegramUpdateDTO.getMessageDTO().setText(telegramUpdateDTO.getMessageDTO().getText().substring(callName.length()).trim());

            } else if (telegramUpdateDTO.getMessageDTO().getReplyToMessage() != null) {
                if (!telegramUpdateDTO.getMessageDTO().getReplyToMessage().getFrom().getUsername().equals(botName))
                    return null;
            } else
                return null;
        }
        Long chatId = telegramUpdateDTO.getMessageDTO().getChat().getId();
        String text = telegramUpdateDTO.getMessageDTO().getText().trim();
        Long messageId = telegramUpdateDTO.getMessageDTO().getMessageId();

        HashMap translated = restService.findTranlate("eng", "tur", text);
        sendMessage(getTranslatedMessage(chatId, translated));
        return null;
    }
}
