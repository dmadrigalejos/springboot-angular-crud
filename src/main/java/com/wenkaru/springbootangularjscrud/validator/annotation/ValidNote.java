package com.wenkaru.springbootangularjscrud.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wenkaru.springbootangularjscrud.validator.validation.NoteValidator;

@Documented
@Constraint(validatedBy = {NoteValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNote {

    String message() default "{" + "com.wenkaru.notes" + ".validator.annotation.ValidNote";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
