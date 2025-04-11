package com.pxkdxvan.node.model;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BotFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(nullable = false, unique = true, updatable = false)
    private UUID shareId = UUID.randomUUID();

    @Column(nullable = false, unique = true, updatable = false)
    private String telegramId;

    @Column(nullable = false, unique = true, updatable = false)
    private String telegramUniqueId;

    @Column(nullable = false, unique = true, updatable = false)
    private String path;

    @Column(nullable = false, updatable = false)
    private Long size;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "binary_id", nullable = false, unique = true, updatable = false)
    private BotBinary binary;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bot_user_id")
    private BotUser owner;

}
