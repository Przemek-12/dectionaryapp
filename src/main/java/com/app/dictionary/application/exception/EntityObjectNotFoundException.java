package com.app.dictionary.application.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityObjectNotFoundException extends RuntimeException {

    private String className;
    private String message;

    public EntityObjectNotFoundException(Class<?> clazz) {
        this.className = clazz.getName();
        this.message = "";
    }

    public EntityObjectNotFoundException(Class<?> clazz, String message) {
        this.className = clazz.getSimpleName();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return new StringBuilder("Entity object not found. entity name: ")
                .append(className)
                .append(" ")
                .append(message)
                .toString();
    }

}
