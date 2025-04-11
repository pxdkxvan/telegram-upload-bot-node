package com.pxkdxvan.node.factory;

import com.pxkdxvan.node.dto.MailPropertiesDTO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class RequestDTOFactory {
    public MailPropertiesDTO createMailProperties(String token, String email) {
        return MailPropertiesDTO
                .builder()
                .token(token)
                .email(email)
                .build();
    }
}
