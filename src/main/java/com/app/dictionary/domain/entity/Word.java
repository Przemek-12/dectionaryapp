package com.app.dictionary.domain.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;
import javax.validation.constraints.NotNull;

@Entity
@Table(
        name = "word",
        indexes = {
                @Index(columnList = "shared_uuid"),
                @Index(columnList = "language, value"),
                @Index(name = "words_langs", columnList = "language, shared_uuid")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"language", "value"}),
                @UniqueConstraint(columnNames = {"shared_uuid", "language", "value"})
        })
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @NonNull
    @NotNull
    @Column(name = "shared_uuid", length = 36)
    @Getter
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID sharedUUID;

    @Enumerated(EnumType.STRING)
    @NonNull
    @NotNull
    @Column(length = 30)
    @Getter
    private Language language;

    @NonNull
    @NotNull
    @Column(length = 100)
    @Getter
    private String value;

    public static Word create(UUID sharedUUID, Language language, String value) {
        return new Word(sharedUUID, language, value);
    }

    private Word(@NonNull UUID sharedUUID, @NonNull Language language, @NonNull String value) {
        this.sharedUUID = sharedUUID;
        this.language = language;
        this.value = value;
    }
}
