package com.pxkdxvan.node.model;

import jakarta.persistence.*;

import lombok.*;

import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bot_user")
public class BotUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private Long telegramId;

    @Column(nullable = false, unique = true)
    private String username;

    @Builder.Default
    private String email = null;
    private String firstName;
    private String lastName;

    @Builder.Default
    @Column(nullable = false)
    private Boolean verified = Boolean.FALSE;

    @Builder.Default
    @Column(nullable = false)
    private Boolean authorized = Boolean.FALSE;

    @CreationTimestamp
    private ZonedDateTime firstLogin;

    @Builder.Default
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BotFile> files = new ArrayList<>();

    public void bindFile(BotFile botFile) {
        files.add(botFile);
        botFile.setOwner(this);
    }

}
