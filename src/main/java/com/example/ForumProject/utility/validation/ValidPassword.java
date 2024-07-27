package com.example.ForumProject.utility.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class) // Link to the validator class
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER }) // Apply to fields, methods, etc.
@Retention(RetentionPolicy.RUNTIME) // Make the annotation available at runtime
public @interface ValidPassword {
    // Default error message
    String message() default "Invalid password";

    // Groups for categorizing constraints
    Class<?>[] groups() default {};

    // Payload for additional data
    Class<? extends Payload>[] payload() default {};
}
