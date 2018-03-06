package com.wenkaru.springbootangularjscrud.dao;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wenkaru.springbootangularjscrud.model.User;

@Repository
@Transactional(readOnly = false)
public class UserDAO extends JdbcDaoSupport {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
        jdbcTemplate = getJdbcTemplate();
    }

    public User get(String username) {
        final String sql = "SELECT * FROM user WHERE username = ?";
        User user;
        
        try {
            user = jdbcTemplate.queryForObject(sql, new Object[] { username }, new BeanPropertyRowMapper<User>(User.class));
        } catch (Exception e) {
            user = new User();
        }
        
        return user;
    }
    
    public List<String> getRoles(String username) {
        final String sql = "SELECT role FROM user_role WHERE username = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(String.class), username);
    }
}
