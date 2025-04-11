package com.pxkdxvan.node.factory;

import com.pxkdxvan.node.model.BotUser;
import com.pxkdxvan.node.model.BotVerification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class BotVerificationFactory {
    public BotVerification create(BotUser botUser) {
        return BotVerification
                .builder()
                .botUser(botUser)
                .build();
    }
}
