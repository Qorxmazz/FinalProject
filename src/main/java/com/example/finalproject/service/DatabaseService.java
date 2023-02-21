package com.example.finalproject.service;

import com.example.finalproject.entity.MyEntity;
import com.example.finalproject.dto.telegram.update.TelegramResponseDTO;
import com.example.finalproject.repository.SaveDbRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseService {
    private final SaveDbRepository repository;
    public void saveToDb(TelegramResponseDTO dto) {
        MyEntity entity = MyEntity.builder().name(dto.getResult().get(0).getUpdateId()).build();
        repository.save(entity);
    }
}
