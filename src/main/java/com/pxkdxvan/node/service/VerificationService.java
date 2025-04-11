package com.pxkdxvan.node.service;

import com.pxkdxvan.node.factory.BotVerificationFactory;
import com.pxkdxvan.node.model.BotUser;
import com.pxkdxvan.node.model.BotVerification;
import com.pxkdxvan.node.repository.BotVerificationRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class VerificationService {

    private final BotVerificationRepository botVerificationRepository;
    private final BotVerificationFactory botVerificationFactory;

    public BotVerification createVerification(BotUser botUser) {
        BotVerification botVerification = botVerificationFactory.create(botUser);
        return botVerificationRepository.save(botVerification);
    }

    @Transactional
    public BotVerification getOrCreateVerification(BotUser botUser) {
        return botVerificationRepository
                .findByBotUser(botUser)
                .orElseGet(() -> createVerification(botUser));
    }

}
