package com.example.finalproject.schedule;

import com.example.finalproject.dto.telegram.update.TelegramResponseDTO;
import com.example.finalproject.dto.telegram.update.TelegramUpdateDTO;
import com.example.finalproject.service.MessageReceiverService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BotSchedule {

//    private final MessageReceiverService messageReceiverService;

    @Scheduled(fixedRate = 500)
    public void hello() {


        RestTemplate restTemplate = new RestTemplate();
        TelegramResponseDTO forObject = restTemplate.getForObject("https://api.telegram.org/bot5945207239:AAHwOHQ4w2iFK4Ho0T4GpS821JgzSDDHW_Q/getUpdates",
                TelegramResponseDTO.class
        );
        int size = forObject.getResult().size();

        if (forObject.getResult().get(0) != null) {

            TelegramUpdateDTO telegramUpdateDTO = forObject.getResult().get(0);
            String text = telegramUpdateDTO.getMessageDTO().getText();
            System.out.println(text);


        }

    }
}
