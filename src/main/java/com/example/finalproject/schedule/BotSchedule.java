package com.example.finalproject.schedule;

import com.example.finalproject.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BotSchedule {


    @Autowired
    RestService restService;

    @Scheduled(fixedRate = 500)
    public void hello() throws IOException {

        restService.getUpdates();


//                TelegramResponseType = jsoupService.jsoupService(text);
//                Long id = telegramUpdateDTO.getMessageDTO().getChat().getId();
//                sendMessage(telegramResponseType, id);
//                System.out.println(text);
    }
}
