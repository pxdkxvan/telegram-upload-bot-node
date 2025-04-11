package com.pxkdxvan.node.service;

import com.pxkdxvan.node.properties.RabbitProperties;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class MessageProducer {

    private final String RABBIT_ROUTING_OUT;

    private final RabbitTemplate rabbitTemplate;

    MessageProducer(RabbitProperties rabbitProperties, RabbitTemplate rabbitTemplate) {
        RABBIT_ROUTING_OUT = rabbitProperties.routing().out();
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produce(SendMessage message) {
        rabbitTemplate.convertAndSend(RABBIT_ROUTING_OUT, message);
    }

}
