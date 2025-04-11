package com.pxkdxvan.node.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.*;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TelegramFileInfoDTO(String fileId, String fileUniqueId, Long fileSize, String filePath) {}
