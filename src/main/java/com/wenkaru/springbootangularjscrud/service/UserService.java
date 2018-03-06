package com.wenkaru.springbootangularjscrud.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.wenkaru.springbootangularjscrud.dao.UserDAO;
import com.wenkaru.springbootangularjscrud.model.User;

@Service
@Configurable
public class UserService {

    @Autowired
    private UserDAO userDao;

    protected static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public User get(String username) {
        return userDao.get(username);
    }
    
    public List<String> getRoles(String username) {
        return userDao.getRoles(username);
    }
}
