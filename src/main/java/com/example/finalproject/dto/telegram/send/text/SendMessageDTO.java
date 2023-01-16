package com.example.finalproject.dto.telegram.send.text;

import com.example.finalproject.dto.telegram.send.ReplyKeyboard;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SendMessageDTO {

    @JsonProperty("chat_id")
    private Long chatId;

    @JsonProperty("text")
    private String text;

    @JsonProperty("reply_markup")
    private ReplyKeyboard replyKeyboard;

}
