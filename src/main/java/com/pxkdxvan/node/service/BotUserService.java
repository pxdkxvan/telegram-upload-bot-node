package com.pxkdxvan.node.service;

import com.pxkdxvan.node.factory.BotUserFactory;
import com.pxkdxvan.node.model.BotUser;
import com.pxkdxvan.node.repository.BotUserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.telegram.telegrambots.meta.api.objects.User;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BotUserService {

    private final BotUserRepository botUserRepository;
    private final BotUserFactory botUserFactory;

    public BotUser createAccount(User telegramUser) {
        BotUser botUser = botUserFactory.create(telegramUser);
        return botUserRepository.save(botUser);
    }

    @Transactional
    public BotUser getOrCreateAccount(User telegramUser) {
        return botUserRepository
                .findByTelegramId(telegramUser.getId())
                .orElseGet(() -> createAccount(telegramUser));
    }

}
