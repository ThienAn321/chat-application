package com.example.backendchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class BackEndChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndChatApplication.class, args);
    }
}
