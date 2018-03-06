package com.wenkaru.springbootangularjscrud.validator.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.wenkaru.springbootangularjscrud.model.Note;
import com.wenkaru.springbootangularjscrud.service.NoteService;
import com.wenkaru.springbootangularjscrud.validator.annotation.ValidNote;

public class NoteValidator implements ConstraintValidator<ValidNote, Note>{
    @Autowired
    private NoteService noteService;

    @Override
    public void initialize(ValidNote constraintAnnotation) {

    }

    @Override
    public boolean isValid(Note note, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        boolean isValid = true;

        if(noteService.isNoteTitleExisting(note)){
            context.buildConstraintViolationWithTemplate("Note title already exists").addPropertyNode("title").addConstraintViolation();

            isValid = false;
        }

        return isValid;
    }
}