package com.pxkdxvan.node.service;

import com.pxkdxvan.node.client.TelegramClient;
import com.pxkdxvan.node.dto.TelegramApiResponseDTO;
import com.pxkdxvan.node.dto.TelegramFileInfoDTO;
import com.pxkdxvan.node.factory.BotFileFactory;
import com.pxkdxvan.node.model.BotBinary;
import com.pxkdxvan.node.model.BotDocument;
import com.pxkdxvan.node.model.BotPhoto;
import com.pxkdxvan.node.repository.BotDocumentRepository;
import com.pxkdxvan.node.repository.BotFileRepository;
import com.pxkdxvan.node.repository.BotPhotoRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.apache.commons.io.IOUtils;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BotFileService {

    private final BotPhotoRepository botPhotoRepository;
    private final BotDocumentRepository botDocumentRepository;

    private final TelegramClient telegramClient;
    private final BotFileFactory botFileFactory;
    private final BotFileRepository botFileRepository;

    @SneakyThrows
    private byte[] getBinary(Resource resource) {
        try (InputStream inputStream = resource.getInputStream()) {
            return IOUtils.toByteArray(inputStream);
        }
    }

    @SneakyThrows
    private TelegramFileInfoDTO fetchTelegramFileInfo(String fileId) {
        return Stream
                .ofNullable(telegramClient.getFileInfo(fileId))
                .map(TelegramApiResponseDTO::result)
                .findFirst()
                .orElseThrow(TelegramApiException::new);
    }

    @SneakyThrows
    private Resource fetchTelegramFileResource(String filePath) {
        return Stream
                .ofNullable(telegramClient.getFile(filePath))
                .findFirst()
                .orElseThrow(TelegramApiException::new);
    }

    private BotBinary fetchBotBinary(TelegramFileInfoDTO telegramFileInfo) {
        String filePath = telegramFileInfo.filePath();
        Resource telegramFileResource = fetchTelegramFileResource(filePath);
        byte[] binary = getBinary(telegramFileResource);
        return botFileFactory.createBinary(binary);
    }

    private BotPhoto fetchBotPhoto(PhotoSize photo, TelegramFileInfoDTO telegramFileInfo) {
        Integer width = photo.getWidth();
        Integer height = photo.getHeight();
        BotBinary binary = fetchBotBinary(telegramFileInfo);
        BotPhoto botPhoto = botFileFactory.createPhoto(telegramFileInfo, width, height, binary);
        return botPhotoRepository.save(botPhoto);
    }

    private BotDocument fetchBotDocument(Document document, TelegramFileInfoDTO telegramFileInfo) {
        String filename = document.getFileName();
        String mimeType = document.getMimeType();
        BotBinary binary = fetchBotBinary(telegramFileInfo);
        BotDocument botDocument = botFileFactory.createDocument(telegramFileInfo, filename, mimeType, binary);
        return botDocumentRepository.save(botDocument);
    }

    @SneakyThrows
    @Transactional
    public BotPhoto processAndFetchPhoto(List<PhotoSize> photos) {
        PhotoSize photo = photos
                .stream()
                .max(Comparator.comparingInt(PhotoSize::getFileSize))
                .orElseThrow(TelegramApiException::new);

        String photoId = photo.getFileId();
        TelegramFileInfoDTO telegramFileInfo = fetchTelegramFileInfo(photoId);
        String fileUniqueId = telegramFileInfo.fileUniqueId();

        return (BotPhoto) botFileRepository
                .findByTelegramUniqueId(fileUniqueId)
                .orElseGet(() -> fetchBotPhoto(photo, telegramFileInfo));
    }

    @Transactional
    public BotDocument processAndFetchDocument(Document document) {
        String documentId = document.getFileId();
        TelegramFileInfoDTO telegramFileInfo = fetchTelegramFileInfo(documentId);
        String fileUniqueId = telegramFileInfo.fileUniqueId();

        return (BotDocument) botFileRepository
                .findByTelegramUniqueId(fileUniqueId)
                .orElseGet(() -> fetchBotDocument(document, telegramFileInfo));
    }

}
