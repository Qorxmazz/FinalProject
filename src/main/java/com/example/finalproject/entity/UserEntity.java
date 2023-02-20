package com.example.finalproject.entity;

import lombok.*;

import javax.persistence.*;
@Data
@Builder
@Entity
@RequiredArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }private String email;
    private String password;
    private String role;
}
