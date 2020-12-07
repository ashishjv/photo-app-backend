package com.example.fireAuth_REST_API.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidateEmail {
    String message() default "Invalid email address - email should contain only: alphabets, digits, '_' , '.' , '@'";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};

    //^[a-zA-Z0-9._]+
    // @{1}
    // [a-zA-Z0-9_]+
    // [.]{1}
    // [a-zA-Z0-9_]+
    // [a-zA-Z0-9._]+$
    String custRegex() default "[a-z]+";
}