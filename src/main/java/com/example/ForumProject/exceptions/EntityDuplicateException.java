package com.example.ForumProject.exceptions;

public class EntityDuplicateException extends RuntimeException {

    public EntityDuplicateException(String type, String attribute, String value) {
        super(String.format("%s with %s %s already exists.", type, attribute, value));
    }
    public EntityDuplicateException(String type, String attribute) {
        super(String.format("%s with id %s already likes this post.", type, attribute));
    }

}
