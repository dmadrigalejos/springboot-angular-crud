package com.wenkaru.springbootangularjscrud.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.wenkaru.springbootangularjscrud.dao.NoteDAO;
import com.wenkaru.springbootangularjscrud.model.Note;

@Service
@Configurable
public class NoteService {

    @Autowired
    private NoteDAO noteDao;

    protected static final Logger LOGGER = LoggerFactory.getLogger(NoteService.class);

    public void add(Note note) {
        noteDao.add(note);
    }
    
    public Map<String, Object> get(String searchQuery, Integer pageNumber, String sortBy, String order, Integer limit) {
        Map<String, Object> notes = new HashMap<>();
        
        try {
            notes = noteDao.get(searchQuery, pageNumber, sortBy, order, limit);
        } catch (Exception e) {
            LOGGER.error(e.toString(), e);
        }
        
        return notes;
    }
    
    public Boolean isNoteTitleExisting(Note note) {
        return noteDao.isNoteTitleExisting(note);
    }
}
