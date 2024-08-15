package com.example.ForumProject.configuration.contracts;

public interface DualConverter<T, U> {
    U convertTo(T source);
    T convertFrom(U source);
}
