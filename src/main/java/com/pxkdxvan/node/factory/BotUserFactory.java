package com.pxkdxvan.node.factory;

import com.pxkdxvan.node.model.BotUser;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class BotUserFactory {
    public BotUser create(User telegramUser) {
        return BotUser
                .builder()
                .telegramId(telegramUser.getId())
                .username(telegramUser.getUserName())
                .firstName(telegramUser.getFirstName())
                .lastName(telegramUser.getLastName())
                .build();
    }
}
