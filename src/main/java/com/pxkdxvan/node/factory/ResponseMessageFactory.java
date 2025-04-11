package com.pxkdxvan.node.factory;

import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public final class ResponseMessageFactory {
    public SendMessage create(Long chatId, String text) {
        return SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
                .build();
    }
}
