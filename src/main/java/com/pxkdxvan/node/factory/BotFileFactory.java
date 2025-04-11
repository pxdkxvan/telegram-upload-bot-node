package com.pxkdxvan.node.factory;

import com.pxkdxvan.node.dto.TelegramFileInfoDTO;
import com.pxkdxvan.node.model.BotBinary;
import com.pxkdxvan.node.model.BotDocument;
import com.pxkdxvan.node.model.BotPhoto;

import org.springframework.stereotype.Component;

@Component
public final class BotFileFactory {

    public BotBinary createBinary(byte[] binary) {
        return BotBinary
                .builder()
                .data(binary)
                .build();
    }

    public BotDocument createDocument(TelegramFileInfoDTO telegramFileInfoDTO, String filename, String mimeType, BotBinary binary) {
        return BotDocument
                .builder()
                .telegramId(telegramFileInfoDTO.fileId())
                .telegramUniqueId(telegramFileInfoDTO.fileUniqueId())
                .path(telegramFileInfoDTO.filePath())
                .size(telegramFileInfoDTO.fileSize())
                .name(filename)
                .mimeType(mimeType)
                .binary(binary)
                .build();
    }

    public BotPhoto createPhoto(TelegramFileInfoDTO telegramFileInfoDTO, Integer width, Integer height, BotBinary binary) {
        return BotPhoto
                .builder()
                .telegramId(telegramFileInfoDTO.fileId())
                .telegramUniqueId(telegramFileInfoDTO.fileUniqueId())
                .path(telegramFileInfoDTO.filePath())
                .width(width)
                .height(height)
                .size(telegramFileInfoDTO.fileSize())
                .binary(binary)
                .build();
    }

}
