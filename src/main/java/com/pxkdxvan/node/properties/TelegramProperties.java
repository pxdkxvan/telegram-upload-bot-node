package com.pxkdxvan.node.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.telegrambots")
public record TelegramProperties(String token) {}
