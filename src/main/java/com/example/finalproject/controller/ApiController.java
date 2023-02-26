package com.example.finalproject.controller;

import com.example.finalproject.service.ApiService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ApiController {

    @Autowired
    ApiService service;
    @SecurityRequirement(name = "Bearer Authentication")

    @GetMapping("/translate/example")
    public String getExample(@RequestParam ("word") String word, @RequestParam("from") String from, @RequestParam("to")
    String to) throws IOException{
        return service.getTranslationResult(word, from, to).toString();
    }
}
