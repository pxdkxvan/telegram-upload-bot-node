package com.pxkdxvan.node.repository;

import com.pxkdxvan.node.model.BotUser;
import com.pxkdxvan.node.model.BotVerification;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BotVerificationRepository extends JpaRepository<BotVerification, Long> {
    Optional<BotVerification> findByBotUser(BotUser botUser);
}
