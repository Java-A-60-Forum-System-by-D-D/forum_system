package com.example.ForumProject.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String type, int id) {
        this(type, "id", String.valueOf(id));
    }

    public EntityNotFoundException(String type, String attribute, String value) {
        super(String.format("%s with %s %s not found.", type, attribute, value));
    }
    public EntityNotFoundException(String type, String attribute) {
        super(String.format("%s with id %s doesn't like this post", type, attribute));
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException() {
        super("Not found url for upload");
    }
}
