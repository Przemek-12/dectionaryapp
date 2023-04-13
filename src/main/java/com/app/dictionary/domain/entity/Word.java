package com.app.dictionary.domain.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;
import javax.validation.constraints.NotNull;

@Entity
@Table(
//        indexes = {
//                @Index(columnList = "value"),
//                @Index(name = "words_langs", columnList = "sharedUUID, language")
//        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"language", "value"}),
                @UniqueConstraint(columnNames = {"sharedUUID", "language", "value"})
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
    @Column(length = 36)
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
