package com.pxkdxvan.node.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class LinkFactory {

    @Value("${application.openfeign.client-uri}/file")
    private String sourceUri;

    public String createSource(String fileId) {
        return UriComponentsBuilder
                .fromPath(sourceUri)
                .pathSegment(fileId)
                .toUriString();
    }

}
