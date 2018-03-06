package com.wenkaru.springbootangularjscrud.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.wenkaru.springbootangularjscrud.validator.annotation.ValidNote;

import lombok.Data;

@Data
@ValidNote
public class Note {
    private Integer id;
    
    @NotBlank(message = "Title is required")
    @Size(max = 64, message = "Title can only accept up to 64 characters")
    private String title;
    
    @NotBlank(message = "Note is required")
    @Size(max = 5120, message = "Note can only accept up to 5120 characters")
    private String note;
}

