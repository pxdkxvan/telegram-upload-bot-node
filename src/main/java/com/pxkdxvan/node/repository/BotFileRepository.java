package com.pxkdxvan.node.repository;

import com.pxkdxvan.node.model.BotFile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BotFileRepository extends JpaRepository<BotFile, Long> {
    Optional<BotFile> findByTelegramUniqueId(String telegramUniqueId);
}
