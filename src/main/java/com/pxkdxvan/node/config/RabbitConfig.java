package com.pxkdxvan.node.config;

import com.pxkdxvan.node.properties.RabbitProperties;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RabbitConfig {

    private final String RABBIT_EXCHANGE;

    private final String RABBIT_QUEUE_IN_TEXT;
    private final String RABBIT_QUEUE_IN_PHOTO;
    private final String RABBIT_QUEUE_IN_DOCUMENT;
    private final String RABBIT_QUEUE_OUT;

    private final String RABBIT_ROUTING_IN_TEXT;
    private final String RABBIT_ROUTING_IN_PHOTO;
    private final String RABBIT_ROUTING_IN_DOC;
    private final String RABBIT_ROUTING_OUT;

    RabbitConfig(RabbitProperties rabbitProperties) {
        RABBIT_EXCHANGE = rabbitProperties.exchange();

        RABBIT_QUEUE_IN_TEXT = rabbitProperties.queue().in().text();
        RABBIT_QUEUE_IN_PHOTO = rabbitProperties.queue().in().photo();
        RABBIT_QUEUE_IN_DOCUMENT = rabbitProperties.queue().in().document();
        RABBIT_QUEUE_OUT = rabbitProperties.queue().out();

        RABBIT_ROUTING_IN_TEXT = rabbitProperties.routing().in().text();
        RABBIT_ROUTING_IN_PHOTO = rabbitProperties.routing().in().photo();
        RABBIT_ROUTING_IN_DOC = rabbitProperties.routing().in().document();
        RABBIT_ROUTING_OUT = rabbitProperties.routing().out();
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(RABBIT_EXCHANGE);
    }

    private Queue queue(String queueName) {
        return new Queue(queueName);
    }

    @Bean
    Queue textQueue() {
        return queue(RABBIT_QUEUE_IN_TEXT);
    }

    @Bean
    Queue photoQueue() {
        return queue(RABBIT_QUEUE_IN_PHOTO);
    }

    @Bean
    Queue responseQueue() {
        return queue(RABBIT_QUEUE_OUT);
    }

    @Bean
    Queue documentQueue() {
        return queue(RABBIT_QUEUE_IN_DOCUMENT);
    }

    private Binding binding(Queue queue, DirectExchange exchange, String with) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(with);
    }

    @Bean
    Binding bindingText(@Qualifier("textQueue") Queue textQueue, DirectExchange exchange) {
        return binding(textQueue, exchange, RABBIT_ROUTING_IN_TEXT);
    }

    @Bean
    Binding bindingPhoto(@Qualifier("photoQueue") Queue photoQueue, DirectExchange exchange) {
        return binding(photoQueue, exchange, RABBIT_ROUTING_IN_PHOTO);
    }

    @Bean
    Binding bindingDocument(@Qualifier("documentQueue") Queue documentQueue, DirectExchange exchange) {
        return binding(documentQueue, exchange, RABBIT_ROUTING_IN_DOC);
    }

    @Bean
    Binding bindingResponse(@Qualifier("responseQueue") Queue responseQueue, DirectExchange exchange) {
        return binding(responseQueue, exchange, RABBIT_ROUTING_OUT);
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(RABBIT_EXCHANGE);
        return rabbitTemplate;
    }

}
