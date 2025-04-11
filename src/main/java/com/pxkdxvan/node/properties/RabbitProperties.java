package com.pxkdxvan.node.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.rabbitmq")
public record RabbitProperties(String exchange, Queue queue, Routing routing) {

    public record Queue(In in, String out) {
        public record In(String text, String photo, String document) {}
    }

    public record Routing(In in, String out) {
        public record In(String text, String photo, String document) {}
    }

}
