package com.pxkdxvan.node;

import com.pxkdxvan.node.model.BotUser;
import com.pxkdxvan.node.model.BotRaw;
import com.pxkdxvan.node.properties.RabbitProperties;
import com.pxkdxvan.node.properties.TelegramProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EntityScan(basePackageClasses = {BotRaw.class, BotUser.class})
@EnableFeignClients
@EnableConfigurationProperties({RabbitProperties.class, TelegramProperties.class})
public class NodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(NodeApplication.class, args);
    }
}
