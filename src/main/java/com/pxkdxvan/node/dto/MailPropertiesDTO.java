package com.pxkdxvan.node.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Builder
public record MailPropertiesDTO(@NotBlank String token, @Email String email) {}
