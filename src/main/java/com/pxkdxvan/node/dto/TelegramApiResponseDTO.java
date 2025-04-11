package com.pxkdxvan.node.dto;

import lombok.*;

@Builder
public record TelegramApiResponseDTO<T>(boolean ok, T result) {}
