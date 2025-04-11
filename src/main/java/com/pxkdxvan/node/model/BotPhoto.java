package com.pxkdxvan.node.model;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bot_photo")
public class BotPhoto extends BotFile {

    @Column(nullable = false, updatable = false)
    private Integer width;

    @Column(nullable = false, updatable = false)
    private Integer height;

}
