package com.pxkdxvan.node.factory;

import com.pxkdxvan.node.model.BotRaw;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class BotRawFactory {
    public BotRaw create(Update raw) {
        return BotRaw
                .builder()
                .raw(raw)
                .build();
    }
}
