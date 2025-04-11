package com.pxkdxvan.node.client;

import com.pxkdxvan.node.dto.TelegramApiResponseDTO;
import com.pxkdxvan.node.dto.TelegramFileInfoDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "telegram", url = "https://api.telegram.org")
public interface TelegramClient {
    @GetMapping("/bot${application.telegrambots.token}/getFile")
    TelegramApiResponseDTO<TelegramFileInfoDTO> getFileInfo(@RequestParam("file_id") String fileId);
    @GetMapping("/file/bot${application.telegrambots.token}/{filepath}")
    Resource getFile(@PathVariable String filepath);
}
