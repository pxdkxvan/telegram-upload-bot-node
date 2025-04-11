package com.pxkdxvan.node.repository;

import com.pxkdxvan.node.model.BotDocument;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotDocumentRepository extends JpaRepository<BotDocument, Long> {}
