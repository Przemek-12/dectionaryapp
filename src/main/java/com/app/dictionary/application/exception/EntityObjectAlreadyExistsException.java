package com.app.dictionary.application.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityObjectAlreadyExistsException extends RuntimeException {

    private String className;

    public EntityObjectAlreadyExistsException(Class<?> clazz) {
        this.className = clazz.getSimpleName();
    }

    @Override
    public String getMessage() {
        return new StringBuilder("Entity object already exists. entity name: ").append(className).toString();
    }

}
