package com.example.finalproject.repository;

import com.example.finalproject.entity.MyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends CrudRepository<MyEntity, Long> {
}
