package com.example.ForumProject.validation;

import com.example.ForumProject.repositories.UserRepositoryImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail,String> {

    private final UserRepositoryImpl userRepository;

    @Autowired
    public UniqueEmailValidator(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.userRepository.getByEmail(value).isEmpty();
    }
}
