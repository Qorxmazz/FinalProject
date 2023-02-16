package com.example.finalproject.dto.telegram.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TelegramResponseDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonProperty("ok")
    private Boolean ok;

    @JsonProperty("result")
    private List<TelegramUpdateDTO> result;

    @JsonProperty("error_code")
    private Long errorCode;

    @JsonProperty("parameters")
    private ParameterDTO parameters;


}
