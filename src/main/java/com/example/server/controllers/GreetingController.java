package com.example.server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GreetingController {

    @GetMapping("/greeting")
    public Map<String, String> greeting() {
        return Map.of(
                "message", "Добро пожаловать в Zvpiolab API!",
                "status", "Сервер работает",
                "timestamp", String.valueOf(System.currentTimeMillis())
        );
    }
}