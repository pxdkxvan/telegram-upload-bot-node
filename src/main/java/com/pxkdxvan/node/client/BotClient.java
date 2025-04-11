package com.pxkdxvan.node.client;

import com.pxkdxvan.node.dto.MailPropertiesDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@FeignClient(name = "${application.openfeign.client-name}",url = "${application.openfeign.client-uri}")
public interface BotClient {
    @GetMapping("/email/send")
    void send(MailPropertiesDTO mailPropertiesDTO);
    @GetMapping("/file/{fid}")
    ResponseEntity<StreamingResponseBody> getFile(@PathVariable("fid") String fileId);
}
