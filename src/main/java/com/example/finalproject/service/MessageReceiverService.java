package com.example.finalproject.service;

import com.example.finalproject.dto.telegram.send.SendMessageResponseDTO;
import com.example.finalproject.dto.telegram.send.text.SendMessageDTO;
import com.example.finalproject.dto.telegram.update.TelegramUpdateDTO;

public interface MessageReceiverService {
    TelegramUpdateDTO getUpdates();

    SendMessageResponseDTO sendMessage(SendMessageDTO sendMessageDTO);

    
}
