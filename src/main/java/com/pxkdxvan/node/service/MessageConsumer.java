package com.pxkdxvan.node.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class MessageConsumer {

    private final MessageProcessor messageProcessor;

    @RabbitListener(queues = {
            "${application.rabbitmq.queue.in.text}",
            "${application.rabbitmq.queue.in.photo}",
            "${application.rabbitmq.queue.in.document}"})
    public void consume(Update update) {
        messageProcessor.process(update);
    }

}
