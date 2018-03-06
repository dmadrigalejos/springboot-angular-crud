package com.wenkaru.springbootangularjscrud.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
}

