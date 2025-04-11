package com.pxkdxvan.node.repository;

import com.pxkdxvan.node.model.BotRaw;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotRawRepository extends JpaRepository<BotRaw, Long> {}
