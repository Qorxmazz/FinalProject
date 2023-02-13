package com.example.finalproject.repository;

import com.example.finalproject.entity.TelegramEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramRepository extends CrudRepository<TelegramEntity,Long> {
    TelegramEntity findByChatId(Long chadId);
}
