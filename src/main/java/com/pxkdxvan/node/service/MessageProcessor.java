package com.pxkdxvan.node.service;

import com.pxkdxvan.node.factory.ResponseMessageFactory;
import com.pxkdxvan.node.factory.BotRawFactory;
import com.pxkdxvan.node.model.*;
import com.pxkdxvan.node.repository.BotRawRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class MessageProcessor {

    private static final String ERROR_MESSAGE = "Sorry, error occurred. Try again later...";

    private final BotRawRepository botRawRepository;

    private final CommandService commandService;
    private final MessageProducer messageProducer;

    private final BotRawFactory botRawFactory;
    private final ResponseMessageFactory responseMessageFactory;

    @Transactional
    public void process(Update update) {
        BotRaw botRaw = botRawFactory.create(update);
        botRawRepository.save(botRaw);

        Long chatId = update.getMessage().getChatId();
        String result;

        try {
            result = commandService.findAndExecuteCommand(update);
        } catch (Exception e) {
            result = ERROR_MESSAGE;
        }

        SendMessage resultMessage = responseMessageFactory.create(chatId, result);
        messageProducer.produce(resultMessage);
    }

}
