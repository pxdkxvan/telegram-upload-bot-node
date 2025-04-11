package com.pxkdxvan.node.repository;

import com.pxkdxvan.node.model.BotPhoto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotPhotoRepository extends JpaRepository<BotPhoto, Long> {}
