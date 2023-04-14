package com.app.dictionary.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PendingWord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

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

    public static PendingWord create(Language language, String value) {
        return new PendingWord(language, value);
    }

    private PendingWord(@NonNull Language language, @NonNull String value) {
        this.language = language;
        this.value = value;
    }
}
