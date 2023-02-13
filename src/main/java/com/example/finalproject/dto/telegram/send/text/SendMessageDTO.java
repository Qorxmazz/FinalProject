package com.example.finalproject.dto.telegram.send.text;

import com.example.finalproject.dto.telegram.send.ReplyKeyboard;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendMessageDTO {

    @JsonProperty("chat_id")
    private Long chatId;

    @JsonProperty("text")
    private String text;


}
