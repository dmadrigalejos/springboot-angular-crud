package com.wenkaru.springbootangularjscrud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wenkaru.springbootangularjscrud.model.Note;

@Repository
@Transactional(readOnly = false)
public class NoteDAO extends JdbcDaoSupport {

    protected static final Logger LOGGER = LoggerFactory.getLogger(NoteDAO.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
        jdbcTemplate = getJdbcTemplate();
    }
    
    public void add(Note note) {
        final String sql = "INSERT INTO note (`title`,`note`) VALUES (?, ?) ";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, note.getTitle());
                ps.setString(2, note.getNote());

                return ps;
            }
        });
    }
    
    public Boolean isNoteTitleExisting(Note note) {
        final String sql = "SELECT count(*) FROM note WHERE title = ? AND id <> ?";
        Boolean exist = false;
        
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, note.getTitle(), note.getId());
            exist = count > 0 ? true : false; 
        } catch (Exception e) {
            exist = false;
        }
        
        return exist;
    }

    public Map<String, Object> get(String searchQuery, Integer pageNumber, String sortBy, String order, Integer limit) {
        Integer offset = pageNumber <= 0 ? 0 : (pageNumber - 1) * limit;

        String sql = "SELECT  `id`,`title`, `note` FROM `note` WHERE concat(`title`,`note`) LIKE ? ORDER BY %s %s LIMIT ?, ?";
        sql = String.format(sql, sortBy, order);
        List<Note> notes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Note>(Note.class), "%" + searchQuery + "%", offset, limit);

        sql = "SELECT COUNT(id) FROM `note` WHERE concat(`title`,`note`) LIKE ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, "%" + searchQuery + "%");

        Map<String, Object> result = new HashMap<>();
        result.put("Notes", notes);
        result.put("TotalRows", count);
        return result;
    }
}
